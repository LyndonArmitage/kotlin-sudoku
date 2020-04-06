package codes.lyndon.sudoku.generator

import codes.lyndon.sudoku.*
import codes.lyndon.sudoku.immutable.ImmutableSudokuGrid
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class BruteForceGenerator(
    private val random: Random = Random(0L)
) : SudokuGenerator {

    private data class Pos(val x: Int, val y: Int)

    override fun generate(): SudokuGrid {
        lateinit var sudoku: SudokuGrid
        do {
            sudoku = ImmutableSudokuGrid.build {
                val positions = ArrayList<Pos>(81)
                for(x in 0 until 9) {
                    for (y in 0 until 9) {
                        positions.add(Pos(x, y))
                    }
                }
                positions.shuffle(random)
                val queue = ArrayDeque(positions)
                while (queue.isNotEmpty()) {
                    val pos = queue.last
                    var isValid: Boolean
                    do {
                        val value = random.nextInt(9) + 1
                        set(pos.x, pos.y, value)
                        isValid = this.build().isValid(mustBeComplete = false)
                    } while (!isValid)
                    queue.removeLast()
                }
            }

        } while (!sudoku.isValid(mustBeComplete = true))
        return sudoku
    }

    private fun randomRow(): List<Int> {
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