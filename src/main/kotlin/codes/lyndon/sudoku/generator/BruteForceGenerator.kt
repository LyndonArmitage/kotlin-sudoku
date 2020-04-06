package codes.lyndon.sudoku.generator

import codes.lyndon.sudoku.ImmutableSudokuGrid
import codes.lyndon.sudoku.SudokuGenerator
import codes.lyndon.sudoku.SudokuGrid
import codes.lyndon.sudoku.isValid
import kotlin.random.Random

class BruteForceGenerator(
    private val random: Random = Random(0L)
) : SudokuGenerator {

    override fun generate(): SudokuGrid {
        lateinit var sudoku: SudokuGrid
        do {
            // This is a very costly generator as it is unlikely to create work
            sudoku = ImmutableSudokuGrid.build {
                // go row by row
                for (y in 0 until 9) {
                    val row = randomRow()
                    for (x in 0 until 9) {
                        set(x, y, row[x])
                    }
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