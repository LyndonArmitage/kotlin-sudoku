package codes.lyndon.sudoku

interface SudokuGrid<out CellGroupType : CellGroup> {

    fun cellAt(x: Int, y: Int): Int?

    operator fun get(x: Int, y: Int): Int? = cellAt(x, y)

    fun boxAt(boxX: Int, boxY: Int): CellGroupType

    fun rowAt(y: Int): CellGroupType

    fun coulmnAt(x: Int): CellGroupType

    companion object {
        const val cellsPerColumn: Int = 9
        const val cellsPerRow: Int = 9
        const val cellsPerGroup: Int = 3
    }
}