package codes.lyndon.sudoku

import codes.lyndon.sudoku.immutable.ImmutableSudokuGrid

object TestUtils {

    val validSudokuNumbers: Array<Int?> = arrayOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9,
        5, 7, 8, 1, 3, 9, 6, 2, 4,
        4, 9, 6, 8, 7, 2, 1, 5, 3,
        9, 5, 2, 3, 8, 1, 4, 6, 7,
        6, 4, 1, 2, 9, 7, 8, 3, 5,
        3, 8, 7, 5, 6, 4, 2, 9, 1,
        7, 1, 9, 6, 2, 3, 5, 4, 8,
        8, 6, 4, 9, 1, 5, 3, 7, 2,
        2, 3, 5, 7, 4, 8, 9, 1, 6
    )

    val validSudokuGrid =
        ImmutableSudokuGrid.builder(validSudokuNumbers).build()

}