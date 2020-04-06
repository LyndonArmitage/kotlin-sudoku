package codes.lyndon.sudoku

import codes.lyndon.sudoku.immutable.ImmutableSudokuGrid
import java.io.File
import java.io.FileReader
import java.io.InputStream
import java.io.Reader
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList

/**
 * Simple Reader that reads sudokus in a simple text format.
 *
 * Ignoring all invalid characters will read the first 81 digits.
 *
 * Treats the following as empty cells: `0`, ` `, or `-`
 */
object SudokuReader {

    @JvmStatic
    fun main(args: Array<String>) {
        val grid = if (args.isEmpty()) {
            read(System.`in`)
        } else {
            read(File(args[0]))
        }

        println(grid)
    }

    fun read(file: File): SudokuGrid =
        read(FileReader(file, StandardCharsets.UTF_8))

    fun read(input: InputStream): SudokuGrid =
        read(input.bufferedReader())

    fun read(reader: Reader): SudokuGrid = reader.use {
        val limit = 81
        val numbers = ArrayList<Int?>(limit)
        val scanner = Scanner(it)
        while (scanner.hasNext() && numbers.size < limit) {
            val next = scanner.next()
            val numbersInString = getNumbersIn(next)

            val currentSize = numbers.size
            val extraNumbers = limit - currentSize - numbersInString.size
            if (extraNumbers > 0) {
                numbers.addAll(
                    numbersInString.subList(0, numbersInString.size - extraNumbers)
                )
                break
            } else {
                numbers.addAll(numbersInString)
            }
        }

        if (numbers.size != limit) {
            throw InvalidSudoku(
                "Wrong count of numbers in Sudoku, expected $limit got ${numbers.size}"
            )
        }

        ImmutableSudokuGrid
            .builder(numbers.toTypedArray())
            .build()
    }

    private fun getNumbersIn(string: String): List<Int?> {
        val list = ArrayList<Int?>(string.length)
        for (char in string) {
            when {
                char == '0' || char == ' ' || char == '-' -> {
                    // blank
                    list.add(null)
                }
                char.isDigit() -> {
                    // 1-9
                    list.add(Character.digit(char, 10))
                }
                else -> {
                    // ignore
                }
            }
        }
        return list
    }

    data class InvalidSudoku(
        override val message: String,
        override val cause: Throwable? = null
    ) : Exception(message, cause)
}