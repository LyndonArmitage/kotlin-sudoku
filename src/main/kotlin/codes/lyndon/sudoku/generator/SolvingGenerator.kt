package codes.lyndon.sudoku.generator

import codes.lyndon.sudoku.*
import codes.lyndon.sudoku.immutable.ImmutableCellGroup
import codes.lyndon.sudoku.mutable.HashMapSudokuGrid
import codes.lyndon.sudoku.mutable.MutableSudokuGrid
import codes.lyndon.sudoku.mutable.Pos
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

private typealias Possibles = MutableSet<Int>

private fun Possibles.pick(random: Random): Int =
    this.toList()[random.nextInt(this.size)]

class SolvingGenerator(
    private val random: Random = Random(1L)
) : SudokuGenerator {

    private val actualGrid: MutableSudokuGrid<ImmutableCellGroup> =
        HashMapSudokuGrid()

    private val possiblesGrid: MutableMap<Pos, Possibles> = HashMap(81)
    private val setCellsQueue = LinkedList<Pos>()

    init {
        for (x in 0 until SudokuGrid.cellsPerRow) {
            for (y in 0 until SudokuGrid.cellsPerColumn) {
                val pos = Pos.of(x, y)
                possiblesGrid[pos] = allPossibles()
            }
        }
    }

    override fun generate(): SudokuGrid<CellGroup> {
        reset()

        // pick empty cell,
        // fill in with valid,
        // if no possible valid values reset last set cells until can

        while (setCellsQueue.size < 81) {
            val pos = randomEmptyPos()
            var possibles = possiblesGrid[pos]
            while (possibles.isNullOrEmpty()) {
                System.err.println("No possibilities for $pos:\n$actualGrid")
                // reset last set cells until can set something here
                val previousPos: Pos = popLastCellChangedAffecting(pos)
                val previousValue = actualGrid[previousPos.x, previousPos.y]
                System.err.println("Reset last cell set: $previousPos = $previousValue")
                actualGrid[previousPos.x, previousPos.y] = null
                recalculatePossiblesAfterChangeAt(previousPos)
                possibles = possiblesGrid[pos]
            }
            val value = possibles.pick(random)
            actualGrid[pos.x, pos.y] = value
            recalculatePossiblesAfterChangeAt(pos)
            setCellsQueue.push(pos)
        }

        if (!actualGrid.isValid(mustBeComplete = true)) {
            throw AssertionError("Somehow made invalid Sudoku:\n$actualGrid")
        }
        return actualGrid.toImmutable()
    }

    private fun popLastCellChangedAffecting(pos: Pos): Pos {
        val cell = getLastCellChangedAffecting(pos)
        setCellsQueue.remove(cell)
        return cell
    }

    private fun getLastCellChangedAffecting(pos: Pos): Pos {
        val filterCells = allCellsRelatedTo(pos)
        return setCellsQueue.last { it in filterCells }
    }

    private fun recalculatePossiblesAfterChangeAt(pos: Pos) {
        val allCells = allCellsRelatedTo(pos)

        for (cell in allCells) {
            recalculatePossiblesFor(cell)
        }
    }

    private fun allCellsRelatedTo(pos: Pos): Set<Pos> {
        val (x, y) = pos
        val box = actualGrid.boxForCellAt(x, y)
        val row = actualGrid.rowAt(y)
        val column = actualGrid.coulmnAt(x)

        return box.cellPositions + row.cellPositions + column.cellPositions
    }

    private fun recalculatePossiblesFor(pos: Pos) {
        val (x, y) = pos
        val possibles = allPossibles()
        possibles.removeAll(actualGrid.boxForCellAt(x, y).presentNumbers)
        possibles.removeAll(actualGrid.rowAt(y).presentNumbers)
        possibles.removeAll(actualGrid.coulmnAt(x).presentNumbers)
        possiblesGrid[pos] = possibles
    }

    private fun reset() {
        resetPossiblesGrid()
        setCellsQueue.clear()
        actualGrid.clear()
    }

    private fun resetPossiblesGrid() {
        for (x in 0 until SudokuGrid.cellsPerRow) {
            for (y in 0 until SudokuGrid.cellsPerColumn) {
                val pos = Pos.of(x, y)
                possiblesGrid[pos] = allPossibles()
            }
        }
    }

    private fun randomEmptyPos(): Pos {
        lateinit var pos: Pos
        do {
            var isEmpty = false
            val possible = randomPos()
            val value = actualGrid[possible.x, possible.y]
            if (value == null) {
                isEmpty = true
                pos = possible
            }
        } while (!isEmpty)
        return pos
    }

    private fun randomPos(): Pos =
        Pos.of(
            random.nextInt(SudokuGrid.cellsPerRow),
            random.nextInt(SudokuGrid.cellsPerColumn)
        )

    companion object {
        private fun allPossibles(): Possibles {
            val range = 1..9
            return range.asIterable().toMutableSet()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val generator = SolvingGenerator()
            val sudoku = generator.generate()
            println(sudoku)
        }
    }

}