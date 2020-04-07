package codes.lyndon.sudoku.renderer

import codes.lyndon.sudoku.TestUtils.validSudokuGrid
import org.junit.jupiter.api.Test

internal class PrettyTextRendererTest {

    @Test
    internal fun `test render`() {
        val render = PrettyTextRenderer.render(validSudokuGrid)
        println(render)
    }
}