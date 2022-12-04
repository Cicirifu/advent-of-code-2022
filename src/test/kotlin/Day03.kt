import kotlin.test.assertEquals

class Day03 {
    private data class Rucksack(val id: Int, val contents: CharSequence) {
        init {
            require(contents.length % 2 == 0) { "Contents size must be even." }
        }

        val firstCompartment = contents.subSequence(0, contents.length / 2)
        val secondCompartment = contents.subSequence(contents.length / 2, contents.length)

        // Guaranteed by assignment to have exactly one item per rucksack
        val duplicateItem = firstCompartment.first { secondCompartment.contains(it) }
    }

    private data class Team(val rucksacks: List<Rucksack>) {
        // Guaranteed by assignment to have exactly one item per team
        val badge = rucksacks
            .map { it.contents }
            .reduce { a, b -> a.filter { b.contains(it) }}
            .first()
    }

    private val Char.priority get() = when (this) {
        in ('a'..'z') -> this - 'a' + 1
        in ('A'..'Z') -> this - 'A' + 27
        else -> throw IllegalArgumentException()
    }

    @Puzzle
    fun solve() {
        val rucksacks = readInput("Day03").asSequence()
            .filter { it.isNotEmpty() }
            .mapIndexed { i, line -> Rucksack(i, line) }
            .toList()

        val duplicateItems = rucksacks.map { it.duplicateItem }

        val firstHalfSolution = duplicateItems.sumOf { it.priority }
        println("Solution A: $firstHalfSolution")
        assertEquals(7997, firstHalfSolution)

        val teams = rucksacks.chunked(3) { Team(it) }
        val badges = teams.map { it.badge }

        val secondHalfSolution = badges.sumOf { it.priority }
        println("Solution B: $secondHalfSolution")
        assertEquals(2545, secondHalfSolution)
    }
}