import java.io.InputStreamReader
import java.lang.Exception

interface Puzzle {
    fun solve()
}

inline fun <T> withInput(inputName: String, body: (Sequence<String>) -> T): T {
    Launcher::class.java.getResource("${inputName}.input")!!.openStream().use { s ->
        return InputStreamReader(s).buffered().useLines(body)
    }
}

fun <T> Sequence<T>.chunkedSplitOn(isBoundary: (T) -> Boolean) = sequence {
    var buffer = mutableListOf<T>()

    this@chunkedSplitOn.forEach { currentValue ->
        val boundary = isBoundary(currentValue)
        if (boundary) {
            yield(buffer)
            buffer = mutableListOf()
            return@forEach
        }
        buffer.add(currentValue)
    }
    if (buffer.isNotEmpty()) {
        yield(buffer)
    }
}

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        val puzzleName = requireNotNull(args.getOrNull(0)) { "Expecting puzzle name." }
        val puzzle = try {
            Class.forName(puzzleName).kotlin.objectInstance as Puzzle
        } catch (ex: Exception) {
            println("Puzzle '${puzzleName}'could not be found.")
            return
        }
        puzzle.solve()
    }
}

