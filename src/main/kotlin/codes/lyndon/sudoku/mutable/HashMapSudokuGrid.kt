package codes.lyndon.sudoku.mutable

import codes.lyndon.sudoku.Sudoku
import codes.lyndon.sudoku.SudokuCell
import codes.lyndon.sudoku.SudokuGrid
import codes.lyndon.sudoku.immutable.ImmutableCellGroup
import codes.lyndon.sudoku.renderer.PrettyTextRenderer

class HashMapSudokuGrid : MutableSudokuGrid<ImmutableCellGroup> {

    private val map: HashMap<Pos, Int?> = HashMap(
        SudokuGrid.cellsPerColumn * SudokuGrid.cellsPerRow
    )

    override fun setCellAt(x: Int, y: Int, value: Int?) {
        val pos = Pos.of(x, y)
        map[pos] = value
    }

    override fun cellAt(x: Int, y: Int): Int? {
        val pos = Pos.of(x, y)
        return map.getOrDefault(pos, null)
    }

    override fun boxAt(boxX: Int, boxY: Int): ImmutableCellGroup {
        val cells = HashSet<SudokuCell>(9)
        val startX: Int = boxX * 3
        val startY: Int = boxY * 3
        for (x in startX until startX + 3) {
            for (y in startY until startY + 3) {
                cells.add(SudokuCell(x, y, get(x, y)))
            }
        }
        return ImmutableCellGroup(cells)
    }

    override fun rowAt(y: Int): ImmutableCellGroup {
        val cells = HashSet<SudokuCell>(SudokuGrid.cellsPerRow)
        for (x in 0 until SudokuGrid.cellsPerRow) {
            cells.add(SudokuCell(x, y, get(x, y)))
        }
        return ImmutableCellGroup(cells)
    }

    override fun coulmnAt(x: Int): ImmutableCellGroup {
        val cells = HashSet<SudokuCell>(SudokuGrid.cellsPerColumn)
        for (y in 0 until SudokuGrid.cellsPerColumn) {
            cells.add(SudokuCell(x, y, get(x, y)))
        }
        return ImmutableCellGroup(cells)
    }

    override fun toString(): String = PrettyTextRenderer.render(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HashMapSudokuGrid

        if (map != other.map) return false

        return true
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }

    companion object {

        fun copyFrom(other: Sudoku): HashMapSudokuGrid {
            val newGrid = HashMapSudokuGrid()
            for (x in 0 until SudokuGrid.cellsPerRow) {
                for (y in 0 until SudokuGrid.cellsPerColumn) {
                    val value: Int? = other[x, y]
                    newGrid[x, y] = value
                }
            }
            return newGrid
        }

    }

}