package codes.lyndon.sudoku

import codes.lyndon.sudoku.renderer.BasicTextRenderer
import java.io.*

/**
 * Writes Sudokus in format [SudokuReader] can read
 */
object SudokuWriter {

    private val renderer = BasicTextRenderer

    fun write(sudoku: Sudoku, file: File) =
        write(sudoku, file.bufferedWriter())

    fun write(sudoku: Sudoku, out: OutputStream) =
        write(sudoku, BufferedWriter(OutputStreamWriter(out)))

    fun write(sudoku: Sudoku, writer: Writer) = writer.use {
        val asString = renderer.render(sudoku)
        it.write(asString)
    }
}