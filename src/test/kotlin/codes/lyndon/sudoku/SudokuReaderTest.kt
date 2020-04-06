package codes.lyndon.sudoku

import codes.lyndon.sudoku.TestUtils.validSudokuGrid
import codes.lyndon.sudoku.TestUtils.validSudokuNumbers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class SudokuReaderTest {


    @Test
    fun `Can read valid Sudoku`() {
        val sudoku = SudokuReader.read(File("src/test/resources/example.sudoku.txt"))
        assertNotNull(sudoku)

        println(sudoku)

        val expectedSudoku = validSudokuGrid
        assertEquals(expectedSudoku, sudoku)
    }
}