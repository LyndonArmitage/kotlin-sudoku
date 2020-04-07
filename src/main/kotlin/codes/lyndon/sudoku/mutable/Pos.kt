package codes.lyndon.sudoku.mutable

class Pos private constructor(val x: Int, val y: Int) {

    companion object {
        fun of(x: Int, y: Int): Pos =
            Pos(x, y) // TODO: Pool x,y coordinates
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