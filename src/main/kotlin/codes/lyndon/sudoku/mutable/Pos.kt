package codes.lyndon.sudoku.mutable

import codes.lyndon.sudoku.Sudoku
import java.util.concurrent.ConcurrentHashMap

class Pos private constructor(val x: Int, val y: Int) {

    companion object {

        private val commonObjectPool = ConcurrentHashMap<Pair<Int, Int>, Pos>()

        init {
            // create a fixed set of shared objects for all common x,y coordinates
            for (x in 0 until Sudoku.cellsPerRow) {
                for (y in 0 until Sudoku.cellsPerColumn) {
                    val pos = Pos(x, y)
                    commonObjectPool[Pair(x, y)] = pos
                }
            }
        }

        fun of(x: Int, y: Int): Pos =
            commonObjectPool[Pair(x, y)] ?: Pos(x, y)
        // We end up creating a Pair(x, y) every invocation but it will be
        // caught very early by GC compared to having multiple Pos(x, y) for
        // the same given (x,y) coordinates.
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pos

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String = "($x,$y)"

    operator fun component1() = x
    operator fun component2() = y

}