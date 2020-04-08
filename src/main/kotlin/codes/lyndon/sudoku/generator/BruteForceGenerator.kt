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

    override fun generate(): Sudoku {
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
                if (sudoku[x, y] != null) {
                    queue.removeLast()
                    continue
                }
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
                    //System.err.println("Possible values exhausted for cell $x,$y:\n$sudoku")
                    // TODO: Fix Me
                    // Possible bodge is to wipe out the box this cell is in of
                    // all values and re-add the positions for generation.
                    // There will be no guarantees this will fix anything but it
                    // is a dumb way of rerolling when stuck in a corner.
                    val startOfBoxX = SudokuGrid.getBoxX(x) * 3
                    val startOfBoxY = SudokuGrid.getBoxY(y) * 3

                    for(wipeX in startOfBoxX until startOfBoxX + 3) {
                        for(wipeY in startOfBoxY until startOfBoxY + 3) {
                            if (wipeX == x && wipeY == y) {
                                continue
                            }
                            queue.add(Pos(wipeX, wipeY))
                            sudoku[wipeX, wipeY] = null
                        }
                    }
                    continue
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