package codes.lyndon.sudoku

interface CellGroup {

    val cells: Set<SudokuCell>

    val presentNumbers: Set<Int>

    val numbersCount: Map<Int?, Int>

    operator fun contains(number: Int): Boolean =
        presentNumbers.contains(number)
}