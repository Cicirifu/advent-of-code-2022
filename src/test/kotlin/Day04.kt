import kotlin.test.assertEquals

class Day04 {
    private data class ElfPair(val a: IntRange, val b: IntRange) {
        companion object {
            fun parse(string: String) = string.split("-", ",").let {
                ElfPair(
                    a = it[0].toInt() .. it[1].toInt(),
                    b = it[2].toInt() .. it[3].toInt()
                )
            }
        }
    }

    private infix fun <T : Comparable<T>> ClosedRange<T>.fullyContains(other: ClosedRange<T>) =
        other.start in this && other.endInclusive in this

    private infix fun <T : Comparable<T>> ClosedRange<T>.overlaps(other: ClosedRange<T>) =
        other.start in this || other.endInclusive in this || other fullyContains this

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