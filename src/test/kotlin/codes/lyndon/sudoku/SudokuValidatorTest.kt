package codes.lyndon.sudoku

import codes.lyndon.sudoku.TestUtils.validSudokuGrid
import codes.lyndon.sudoku.generator.BadGenerator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SudokuValidatorTest {

    @Test
    internal fun `validates a correct sudoku when complete`() {
        val sudoku = validSudokuGrid
        assertTrue(sudoku.isValid(mustBeComplete = true))
    }

    @Test
    internal fun `validates  when expecting incomplete sudoku`() {
        val sudoku = ImmutableSudokuGrid
            .builder(validSudokuGrid)
            .set(0, 0, null)
            .build()
        assertTrue(sudoku.isValid(mustBeComplete = false))
    }

    @Test
    internal fun `fails to validate  when expecting complete sudoku`() {
        val sudoku = ImmutableSudokuGrid
            .builder(validSudokuGrid)
            .set(0, 0, null)
            .build()
        assertFalse(sudoku.isValid(mustBeComplete = true))
    }

    @Test
    internal fun `fails to validate a bad sudoku`() {
        val sudoku = ImmutableSudokuGrid
            .builder(validSudokuGrid)
            .set(0, 0, 9)
            .build()
        assertFalse(sudoku.isValid(mustBeComplete = true))
    }

    @Test
    internal fun `fails to validate a bad generated sudoku`() {
        assertFalse(BadGenerator.generate().isValid())
    }
}