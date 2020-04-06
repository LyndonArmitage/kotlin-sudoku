package codes.lyndon.sudoku.generator

import codes.lyndon.sudoku.CellGroup
import codes.lyndon.sudoku.immutable.ImmutableSudokuGrid
import codes.lyndon.sudoku.SudokuGenerator
import codes.lyndon.sudoku.SudokuGrid

object BadGenerator : SudokuGenerator {

    @JvmStatic
    fun main(args: Array<String>) {
        println(generate())
    }

    override fun generate(): SudokuGrid<CellGroup> {

        return ImmutableSudokuGrid.build {
            for (y in 0 until SudokuGrid.cellsPerColumn) {
                for (x in 0 until SudokuGrid.cellsPerRow) {
                    set(x, y, y + 1)
                }
            }
        }
    }
}