object Day01 : Puzzle {
    data class Elf(val id: Int, val calories: List<Int>) {
        val totalCalories = calories.sum()
    }

    override fun solve() {
        val elves = withInput("Day01") { input -> input
            .chunkedSplitOn { it.isEmpty() }
            .map { it.map(String::toInt) }
            .mapIndexed { id, calories -> Elf(id, calories) }
            .toList()
        }

        val mostCalorieRichElf = elves.maxBy { it.totalCalories }

        val firstHalfSolution = mostCalorieRichElf.totalCalories
        println("Solution A: $firstHalfSolution, $mostCalorieRichElf")
        assert(firstHalfSolution == 68775)

        val topKCalorieRichElves = elves
            .sortedByDescending { it.totalCalories }
            .take(3)

        val secondHalfSolution = topKCalorieRichElves.sumOf { it.totalCalories }
        println("Solution B: $secondHalfSolution, $topKCalorieRichElves")
        assert(secondHalfSolution == 202585)
    }
}
