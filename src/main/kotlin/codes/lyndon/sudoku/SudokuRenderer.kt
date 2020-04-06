package codes.lyndon.sudoku

interface SudokuRenderer<RenderType> {

    fun render(sudoku: SudokuGrid): RenderType
}