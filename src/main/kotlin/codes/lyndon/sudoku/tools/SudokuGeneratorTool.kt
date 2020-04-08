package codes.lyndon.sudoku.tools

import codes.lyndon.sudoku.*
import codes.lyndon.sudoku.generator.SolvingGenerator
import codes.lyndon.sudoku.renderer.BasicTextRenderer
import codes.lyndon.sudoku.renderer.PrettyTextRenderer
import picocli.CommandLine
import java.util.concurrent.Callable
import kotlin.random.Random
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "SudokuGeneratorTool",
    version = ["alpha"],
    mixinStandardHelpOptions = true,
    description = [
        "Simple Sudoku generation tool"
    ]
)
class SudokuGeneratorTool : Callable<Int> {

    private enum class RenderType {
        Pretty,
        Basic
    }

    @CommandLine.Option(
        names = ["-c", "--count"],
        paramLabel = "COUNT",
        description = [
            "Count of Sudokus to generate"
        ],
        defaultValue = "1",
        showDefaultValue = CommandLine.Help.Visibility.ALWAYS
    )
    private var countToGenerate: Int = 1

    @CommandLine.Option(
        names = ["-s", "--seed"],
        paramLabel = "SEED",
        description = [
            "A set seed to use for random generation"
        ]
    )
    private var seed: Long? = null

    @CommandLine.Option(
        names = [ "-f", "--format"],
        paramLabel = "FORMAT",
        description = [
            "Set the format type for the output.",
            "From possibles:",
            "\${COMPLETION-CANDIDATES}"
        ],
        defaultValue = "Basic",
        showDefaultValue = CommandLine.Help.Visibility.ALWAYS
    )
    private var format: RenderType = RenderType.Basic

    override fun call(): Int {
        val generator: SudokuGenerator = try {
            createGenerator(this.seed)
        } catch (e: Exception) {
            System.err.println("Issue creating Sudoku Generator")
            e.printStackTrace()
            return 1
        }

        val renderer: SudokuRenderer<String> = getRenderer()

        repeat(countToGenerate) {
            val sudoku: Sudoku = try {
                generator.generate()
            } catch (e: Exception) {
                System.err.println("Issue creating Sudoku $it")
                e.printStackTrace()
                return 1
            }

            try {
                output(sudoku, renderer)
            } catch (e: Exception) {
                System.err.println("Issue rendering Sudoku $it")
                e.printStackTrace()
                return 1
            }
        }

        return 0
    }

    private fun getRenderer(): SudokuRenderer<String> = when(format) {
        RenderType.Basic -> BasicTextRenderer
        RenderType.Pretty -> PrettyTextRenderer
    }

    private fun createGenerator(setSeed: Long?): SudokuGenerator {
        val seed: Long = setSeed ?: System.currentTimeMillis()
        val random = Random(seed)
        return SolvingGenerator(random)
    }

    private fun output(
        sudoku: Sudoku,
        renderer: SudokuRenderer<String>
    ) {
        val render = renderer.render(sudoku)
        println(render)
    }

}

fun main(args: Array<String>) {
    val commandLine = CommandLine(SudokuGeneratorTool())
    commandLine.isCaseInsensitiveEnumValuesAllowed = true
    exitProcess(commandLine.execute(*args))
}