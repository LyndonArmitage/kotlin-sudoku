package codes.lyndon.sudoku.mutable

import codes.lyndon.sudoku.CellGroup
import codes.lyndon.sudoku.SudokuGrid
import codes.lyndon.sudoku.immutable.ImmutableSudokuGrid

interface MutableSudokuGrid<CellGroupType : CellGroup> : SudokuGrid<CellGroupType> {

    fun setCellAt(x: Int, y: Int, value: Int?)

    operator fun set(x: Int, y: Int, value: Int?) = setCellAt(x, y, value)

    fun toImmutable(): ImmutableSudokuGrid {
        val self = this
        return ImmutableSudokuGrid.build {
            for (x in 0 until SudokuGrid.cellsPerRow) {
                for (y in 0 until SudokuGrid.cellsPerColumn) {
                    val value: Int? = self[x, y]
                    this[x, y] = value
                }
            }
        }
    }
}