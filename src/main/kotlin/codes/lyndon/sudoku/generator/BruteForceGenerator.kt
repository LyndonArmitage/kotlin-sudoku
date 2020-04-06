package codes.lyndon.sudoku.generator

import codes.lyndon.sudoku.*
import codes.lyndon.sudoku.mutable.HashMapSudokuGrid
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class BruteForceGenerator(
    private val random: Random = Random(0L)
) : SudokuGenerator {

    private data class Pos(val x: Int, val y: Int)

    override fun generate(): SudokuGrid<CellGroup> {
        val sudoku = HashMapSudokuGrid()
        var count: Long = 0L
        do {
            val positions = ArrayList<Pos>(81)
            for (x in 0 until 9) {
                for (y in 0 until 9) {
                    positions.add(Pos(x, y))
                }
            }
            positions.shuffle(random)
            val queue = ArrayDeque(positions)
            while (queue.isNotEmpty()) {
                val pos = queue.last
                val possibleValues = randomOrderedNumbers()
                val (x, y) = pos
                possibleValues.removeAll(
                    sudoku.boxForCellAt(x, y).presentNumbers
                )
                possibleValues.removeAll(
                    sudoku.rowAt(y).presentNumbers
                )
                possibleValues.removeAll(
                    sudoku.coulmnAt(x).presentNumbers
                )
                if (possibleValues.isEmpty()) {
                    // Problem persists that we end up making a lot more
                    // bad configurations than good here.
                    // At least we are getting closer though
                    System.err.println(
                        "Possible values exhausted for cell $x,$y:\n$sudoku"
                    )
                }
                val value = possibleValues.first()
                sudoku[pos.x, pos.y] = value
                queue.removeLast()
            }

            count ++
            if (count % 1000L == 0L) {
                println(count)
            }
        } while (!sudoku.isValid(mustBeComplete = true))
        return sudoku
    }

    private fun randomOrderedNumbers(): MutableList<Int> {
        val range = 1..9
        val list = range.asIterable().toMutableList()
        list.shuffle(random)
        return list
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val sudoku = BruteForceGenerator().generate()
            println(sudoku)
        }
    }
}