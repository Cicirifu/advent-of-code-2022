import kotlin.test.assertEquals

class Day04 {
    private data class ElfPair(val a: IntRange, val b: IntRange) {
        companion object {
            fun parse(string: String) = string.split("-", ",").let { (a0, a1, b0, b1) ->
                ElfPair(
                    a = a0.toInt() .. a1.toInt(),
                    b = b0.toInt() .. b1.toInt()
                )
            }
        }
    }

    private infix fun <T : Comparable<T>> ClosedRange<T>.fullyContains(other: ClosedRange<T>) =
        start <= other.start && endInclusive >= other.endInclusive

    private infix fun <T : Comparable<T>> ClosedRange<T>.overlaps(other: ClosedRange<T>) =
        start <= other.endInclusive && other.start <= endInclusive

    @Puzzle
    fun solve() {
        val pairs = readInput("Day04").asSequence()
            .filter { it.isNotEmpty() }
            .map { ElfPair.parse(it) }

        val fullyContainingPairs = pairs
            .filter { (a, b) -> a fullyContains b || b fullyContains a }

        val firstHalfSolution = fullyContainingPairs.count()
        println("Solution A: $firstHalfSolution")
        assertEquals(562, firstHalfSolution)

        val overlappingPairs = pairs
            .filter { (a, b) -> a overlaps b }

        val secondHalfSolution = overlappingPairs.count()
        println("Solution B: $secondHalfSolution")
        assertEquals(924, secondHalfSolution)
    }
}