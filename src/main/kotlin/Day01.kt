data class Elf(val id: Int, val calories: List<Int>) {
    val totalCalories = calories.sum()
}

fun main() = withInput("Day01") { input ->
    val elves = input.chunkedSplitOn { it.isEmpty() }
        .map { it.map(String::toInt) }
        .mapIndexed { id, calories -> Elf(id, calories) }
        .toList()

    val mostCalorieRichElf = elves
        .maxBy { it.totalCalories }

    println("Solution #1: $mostCalorieRichElf, total: ${mostCalorieRichElf.totalCalories}")

    val topKCalorieRichElves = elves
        .sortedByDescending { it.totalCalories }
        .take(3).toList()

    println("Solution #2: $topKCalorieRichElves, total: ${topKCalorieRichElves.sumOf { it.totalCalories }}")
}
