package codes.lyndon.sudoku

typealias Sudoku = SudokuGrid<CellGroup>

interface SudokuGrid<out CellGroupType : CellGroup> {

    fun cellAt(x: Int, y: Int): Int?

    operator fun get(x: Int, y: Int): Int? = cellAt(x, y)

    fun isSet(x: Int, y: Int): Boolean = cellAt(x, y) != null

    fun boxAt(boxX: Int, boxY: Int): CellGroupType

    fun boxForCellAt(x: Int, y: Int): CellGroupType =
        boxAt(getBoxX(x), getBoxY(y))

    fun rowAt(y: Int): CellGroupType

    fun coulmnAt(x: Int): CellGroupType

    companion object {
        const val cellsPerColumn: Int = 9
        const val cellsPerRow: Int = 9
        const val cellsPerGroup: Int = 3

        fun getBoxX(x: Int): Int = x / cellsPerGroup

        fun getBoxY(y: Int): Int = y / cellsPerGroup
    }
}