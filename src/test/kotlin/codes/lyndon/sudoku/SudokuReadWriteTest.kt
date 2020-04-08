package codes.lyndon.sudoku

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

internal class SudokuReadWriteTest {
    @Test
    internal fun `can write and read back`() {
        val sudoku = TestUtils.validSudokuGrid
        val out = ByteArrayOutputStream()
        SudokuWriter.write(sudoku, out)

        val input = ByteArrayInputStream(out.toByteArray())
        val readBack = SudokuReader.read(input)

        assertEquals(sudoku, readBack)
    }
}