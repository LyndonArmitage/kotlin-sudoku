package codes.lyndon.sudoku.mutable

import codes.lyndon.sudoku.CellGroup
import codes.lyndon.sudoku.SudokuGrid

interface MutableSudokuGrid<CellGroupType : CellGroup> : SudokuGrid<CellGroupType> {

    fun setCellAt(x: Int, y: Int, value: Int?)

    operator fun set(x: Int, y: Int, value: Int?) = setCellAt(x, y, value)
}