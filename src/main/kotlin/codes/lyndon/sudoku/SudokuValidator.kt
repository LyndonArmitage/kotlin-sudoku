package codes.lyndon.sudoku

fun Sudoku.isValid(mustBeComplete: Boolean = false): Boolean {

    for (y in 0 until SudokuGrid.cellsPerColumn) {
        if (!this.rowAt(y).isValid(mustBeComplete)) {
            return false
        }
    }

    for (x in 0 until SudokuGrid.cellsPerRow) {
        if (!this.coulmnAt(x).isValid(mustBeComplete)) {
            return false
        }
    }

    for (x in 0 until SudokuGrid.cellsPerGroup) {
        for (y in 0 until SudokuGrid.cellsPerGroup) {
            if (!this.boxAt(x, y).isValid(mustBeComplete)) {
                return false
            }
        }
    }


    return true
}

fun CellGroup.isValid(mustBeComplete: Boolean = false): Boolean {
    for (number in 1..9) {
        val count = numbersCount.getOrDefault(number, 0)
        if (count > 1) {
            // too many instances
            return false
        }
        if (mustBeComplete && count <= 0) {
            return false
        }
    }
    return true
}
