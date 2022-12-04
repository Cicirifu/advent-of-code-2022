import kotlin.test.assertEquals

class Day01 {
    private data class Elf(val id: Int, val calories: List<Int>) {
        val totalCalories = calories.sum()
    }

    @Puzzle
    fun solve() {
        val elves = readInput("Day01").asSequence()
            .chunkedSplitOn { it.isEmpty() }
            .map { it.map(String::toInt) }
            .mapIndexed { id, calories -> Elf(id, calories) }
            .toList()

        val mostCalorieRichElf = elves.maxBy { it.totalCalories }

        val firstHalfSolution = mostCalorieRichElf.totalCalories
        println("Solution A: $firstHalfSolution, $mostCalorieRichElf")
        assertEquals(68775, firstHalfSolution)

        val topKCalorieRichElves = elves
            .sortedByDescending { it.totalCalories }
            .take(3)

        val secondHalfSolution = topKCalorieRichElves.sumOf { it.totalCalories }
        println("Solution B: $secondHalfSolution, $topKCalorieRichElves")
        assertEquals(202585, secondHalfSolution)
    }
}
