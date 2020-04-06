package codes.lyndon.sudoku.immutable

import codes.lyndon.sudoku.CellGroup
import codes.lyndon.sudoku.SudokuCell
import kotlin.streams.toList

class ImmutableCellGroup constructor(
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