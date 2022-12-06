import kotlin.test.assertEquals

class Day05 {
    private data class CrateMover<S, E>(val containerYard: Map<S, MutableList<E>>) {
        tailrec fun moveSingle(amount: Int, from: S, to: S) {
            containerYard[to]!!.add(containerYard[from]!!.removeLast())

            if (amount > 1) {
                moveSingle(amount - 1, from, to)
            }
        }

        fun moveAsBlock(amount: Int, from: S, to: S) {
            val items = containerYard[from]!!.takeLast(amount)
            containerYard[from]!!.let { list -> repeat(amount) { list.removeLast() } }
            containerYard[to]!!.addAll(items)
        }
    }

    private fun CrateMover<Char, Char>.topView() = containerYard.asSequence()
        .sortedBy { it.key }
        .map { it.value.last() }
        .joinToString("")

    private data class MoveCommand(val amount: Int, val from: Char, val to: Char) {
        companion object {
            private val REGEX = Regex("^move (\\d+) from (\\d) to (\\d)\\s*$")

            fun parse(line: String) = REGEX.matchEntire(line)?.destructured
                ?.let { (amount, from, to) -> MoveCommand(amount.toInt(), from.first(), to.first()) }
                ?: throw IllegalArgumentException()
        }
    }

    // Assume whitespace if out of range to handle jagged arrays
    private fun List<String>.readTransposedLine(index: Int) = this
        .map { it.getOrElse(index) { ' ' } }
        .joinToString("")

    private fun parseContainerStacks(data: List<String>): CrateMover<Char, Char> {
        val headerLine = data.last()
        val stackData = data.asReversed().drop(1)

        val dataMap = headerLine
            .withIndex()
            .filter { (_, it) -> !it.isWhitespace() }
            .associate { (stringIndex, stackSymbol) -> stackSymbol to stackData.readTransposedLine(stringIndex).trim() }

        return CrateMover(
            containerYard = dataMap.mapValues { (_, value) -> value.toMutableList() }
        )
    }

    @Puzzle
    fun solve() {
        val lines = readInput("Day05")
        val containerYard = lines.takeWhile { it.isNotBlank() }
        val commands = lines.asSequence()
            .dropWhile { !it.startsWith("move") }
            .map { MoveCommand.parse(it) }

        val crateMover9000 = parseContainerStacks(containerYard)
        commands.forEach { (amount, from, to) ->
            crateMover9000.moveSingle(amount, from, to)
        }

        val firstHalfSolution = crateMover9000.topView()
        println("Solution A: $firstHalfSolution")
        assertEquals("QMBMJDFTD", firstHalfSolution)

        val crateMover9001 = parseContainerStacks(containerYard)
        commands.forEach { (amount, from, to) ->
            crateMover9001.moveAsBlock(amount, from, to)
        }

        val secondHalfSolution = crateMover9001.topView()
        println("Solution B: $secondHalfSolution")
        assertEquals("NBTVTJNFJ", secondHalfSolution)
    }
}
