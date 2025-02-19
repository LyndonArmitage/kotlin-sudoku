package codes.lyndon.sudoku.renderer

import codes.lyndon.sudoku.Sudoku
import codes.lyndon.sudoku.SudokuGrid
import codes.lyndon.sudoku.SudokuRenderer

object BasicTextRenderer : SudokuRenderer<String> {

    override fun render(sudoku: Sudoku): String {
        val stringBuilder = StringBuilder(
            (SudokuGrid.cellsPerRow * SudokuGrid.cellsPerColumn) +
                    SudokuGrid.cellsPerColumn
        )

        return render(sudoku, stringBuilder)
    }

    private fun render(sudoku: Sudoku, builder: StringBuilder): String {
        for (y in 0 until SudokuGrid.cellsPerRow) {
            for (x in 0 until SudokuGrid.cellsPerColumn) {
                val value = sudoku[x, y]
                val string = if (value == null) {
                    " "
                } else {
                    "$value"
                }
                builder.append(string)
            }
            builder.appendln()
        }
        return builder.toString()
    }

}