package codes.lyndon.sudoku

data class SudokuCell(
    val x: Int,
    val y: Int,
    val value: Int?
) {
    val pos: Pos by lazy { Pos.of(x, y) }
}