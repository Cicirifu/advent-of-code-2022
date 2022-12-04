typealias Puzzle = kotlin.test.Test

object Util

fun readInput(inputName: String): List<String> {
    Util::class.java.getResource("${inputName}.input")!!.openStream().use { s ->
        return s.bufferedReader(Charsets.UTF_8).readLines()
    }
}
inline fun <T> withInput(inputName: String, body: (Sequence<String>) -> T): T {
    Util::class.java.getResource("${inputName}.input")!!.openStream().use { s ->
        return s.bufferedReader(Charsets.UTF_8).useLines(body)
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

