package codes.lyndon.sudoku

interface SudokuGrid {

    fun cellAt(x: Int, y: Int): Int?

    operator fun get(x: Int, y: Int): Int? = cellAt(x, y)

    fun boxAt(boxX: Int, boxY: Int): CellGroup

    fun rowAt(y: Int): CellGroup

    fun coulmnAt(x: Int): CellGroup

    companion object {
        const val cellsPerColumn: Int = 9
        const val cellsPerRow: Int = 9
        const val cellsPerGroup: Int = 3
    }
}