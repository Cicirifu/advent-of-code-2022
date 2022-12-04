import kotlin.test.assertEquals

class Day02 {
    // Ordinals of enums deliberately chosen to make maths work.
    private enum class Outcome(val score: Int) {
        DRAW(3),
        WIN(6),
        LOSS(0),
        ;

        object OrdinalOrder : List<Outcome> by values().toList()
        object DeserializationOrder : List<Outcome> by listOf(LOSS, DRAW, WIN)
    }

    private enum class Move(val score: Int) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3),
        ;

        object OrdinalOrder : List<Move> by values().toList()
        object DeserializationOrder : List<Move> by values().toList()
    }

    private data class Match(val id: Int, val playerMove: Move, val opponentMove: Move) {
        // Pitfall: consider behaviour of modulo with negative values (`rem` vs `mod`)
        val outcome = Outcome.OrdinalOrder[(playerMove.ordinal - opponentMove.ordinal).mod(3)]
        val score = outcome.score + playerMove.score
    }

    private data class CorrectMatch(val id: Int, val opponentMove: Move, val desiredOutcome: Outcome) {
        val playerMove = Move.OrdinalOrder[((opponentMove.ordinal + desiredOutcome.ordinal).mod(3))]
        val score = desiredOutcome.score + playerMove.score
    }

    @Puzzle
    fun solve() {
        val matches = readInput("Day02").asSequence()
            .filter { it.isNotEmpty() }
            .mapIndexed { i, line ->
                Match(
                    id = i,
                    opponentMove = Move.DeserializationOrder[line[0] - 'A'],
                    playerMove = Move.DeserializationOrder[line[2] - 'X']
                )
            }

        val firstHalfSolution = matches.sumOf { it.score }
        println("Solution A: $firstHalfSolution")
        assertEquals(13675, firstHalfSolution)

        val correctMatches = readInput("Day02").asSequence()
            .filter { it.isNotEmpty() }
            .mapIndexed { i, line ->
                CorrectMatch(
                    id = i,
                    opponentMove = Move.DeserializationOrder[line[0] - 'A'],
                    desiredOutcome = Outcome.DeserializationOrder[line[2] - 'X']
                )
            }

        val secondHalfSolution = correctMatches.sumOf { it.score }
        println("Solution B: $secondHalfSolution")
        assertEquals(14184, secondHalfSolution)
    }
}
