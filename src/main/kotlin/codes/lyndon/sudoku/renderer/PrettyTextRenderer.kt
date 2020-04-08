package codes.lyndon.sudoku.renderer

import codes.lyndon.sudoku.Sudoku
import codes.lyndon.sudoku.SudokuGrid
import codes.lyndon.sudoku.SudokuRenderer

object PrettyTextRenderer : SudokuRenderer<String> {

    override fun render(sudoku: Sudoku): String {
        val stringBuilder = StringBuilder(
            (SudokuGrid.cellsPerRow * SudokuGrid.cellsPerColumn) +
                    SudokuGrid.cellsPerColumn
        )

        return render(sudoku, stringBuilder)
    }

    private fun render(sudoku: Sudoku, builder: StringBuilder): String {
        builder.append("┌───┬───┬───┐").appendln()
        for (y in 0 until SudokuGrid.cellsPerRow) {
            if(y != 0 && y % 3 == 0) {
                builder.append("├───┼───┼───┤").appendln()
            }
            for (x in 0 until SudokuGrid.cellsPerColumn) {
                if (x == 0 || x % 3 == 0) {
                    builder.append("│")
                }
                val value = sudoku[x, y]
                val string = if (value == null) {
                    " "
                } else {
                    "$value"
                }
                builder.append(string)
                if (x == 8) {
                    builder.append("│")
                }

            }
            builder.appendln()
        }
        builder.append("└───┴───┴───┘").appendln()
        return builder.toString()
    }

}