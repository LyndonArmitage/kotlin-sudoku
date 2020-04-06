package codes.lyndon.sudoku.immutable

import codes.lyndon.sudoku.CellGroup
import codes.lyndon.sudoku.IllegalSizedSudoku
import codes.lyndon.sudoku.SudokuCell
import codes.lyndon.sudoku.SudokuGrid
import codes.lyndon.sudoku.renderer.BasicTextRenderer
import kotlin.streams.toList

class ImmutableSudokuGrid private constructor(
    grid: Array<Int?>
) : SudokuGrid {

    private val gridData: Array<Int?>

    init {
        val expectedSize = SudokuGrid.cellsPerRow * SudokuGrid.cellsPerColumn
        if (grid.size != expectedSize) {
            throw IllegalSizedSudoku(grid)
        } else {
            gridData = grid.copyOf()
        }
    }

    override fun cellAt(x: Int, y: Int): Int? {
        val index = (y * SudokuGrid.cellsPerRow) + x
        return gridData[index]
    }

    override fun boxAt(boxX: Int, boxY: Int): CellGroup {
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

    override fun rowAt(y: Int): CellGroup {
        val cells = HashSet<SudokuCell>(SudokuGrid.cellsPerRow)
        for (x in 0 until SudokuGrid.cellsPerRow) {
            cells.add(SudokuCell(x, y, get(x, y)))
        }
        return ImmutableCellGroup(cells)
    }

    override fun coulmnAt(x: Int): CellGroup {
        val cells = HashSet<SudokuCell>(SudokuGrid.cellsPerColumn)
        for (y in 0 until SudokuGrid.cellsPerColumn) {
            cells.add(SudokuCell(x, y, get(x, y)))
        }
        return ImmutableCellGroup(cells)
    }

    override fun toString(): String {
        return BasicTextRenderer.render(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImmutableSudokuGrid

        if (!gridData.contentEquals(other.gridData)) return false

        return true
    }

    override fun hashCode(): Int {
        return gridData.contentHashCode()
    }


    companion object {
        val emptySudoku =
            ImmutableSudokuGrid(emptyGrid())

        fun builder(): Builder =
            Builder()

        fun builder(gridData: Array<Int?>): Builder =
            Builder(gridData.copyOf())

        fun builder(baseSudoku: SudokuGrid): Builder {
            val grid = emptyGrid()
            for (x in 0 until SudokuGrid.cellsPerRow) {
                for (y in 0 until SudokuGrid.cellsPerColumn) {
                    grid[indexOf(x, y)] = baseSudoku.cellAt(x, y)
                }
            }
            return Builder(grid)
        }

        fun build(function: Builder.() -> Unit): ImmutableSudokuGrid {
            val builder = Builder()
            builder.function()
            return builder.build()
        }

        private fun indexOf(x: Int, y: Int): Int =
            (y * SudokuGrid.cellsPerRow) + x

        private fun emptyGrid(): Array<Int?> = Array(
            SudokuGrid.cellsPerRow * SudokuGrid.cellsPerColumn
        ) { null }
    }

    private class ImmutableCellGroup internal constructor(
        cells: Collection<SudokuCell>
    ) : CellGroup {
        override val cells = cells.toSet()

        override val presentNumbers: Set<Int> by lazy {
            this.cells
                .mapNotNull { it.value }
                .stream()
                .mapToInt { it }
                .toList()
                .toSet()
        }

        override val numbersCount: Map<Int?, Int> by lazy {
            val map = HashMap<Int?, Int>(9)
            for (cell in cells) {
                val value = cell.value
                map[value] = map.getOrPut(value) { 0 } + 1
            }

            map.toMap()
        }
    }


    class Builder internal constructor(
        private val gridData: Array<Int?> = emptyGrid()
    ) {

        fun setCell(x: Int, y: Int, value: Int?): Builder {
            gridData[indexOf(x, y)] = value
            return this
        }

        operator fun set(x: Int, y: Int, value: Int?): Builder {
            setCell(x, y, value)
            return this
        }

        fun build(): ImmutableSudokuGrid =
            ImmutableSudokuGrid(gridData)

        override fun toString(): String {
            return build().toString()
        }
    }
}