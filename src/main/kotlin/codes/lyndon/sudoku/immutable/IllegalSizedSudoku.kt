package codes.lyndon.sudoku.immutable

class IllegalSizedSudoku(
    data: Array<Int?>
) : Exception("Illegal sized Sudoku data with ${data.size} entries") {
    val badData = data.copyOf()
}