object Day02 : Puzzle {
    // Ordinals of enums deliberately chosen to make maths work.
    enum class Outcome(val score: Int) {
        DRAW(3),
        WIN(6),
        LOSS(0),
        ;

        object OrdinalOrder : List<Outcome> by values().toList()
        object DeserializationOrder : List<Outcome> by listOf(LOSS, DRAW, WIN)
    }

    enum class Move(val score: Int) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3),
        ;

        object OrdinalOrder : List<Move> by values().toList()
        object DeserializationOrder : List<Move> by values().toList()
    }

    data class Match(val id: Int, val playerMove: Move, val opponentMove: Move) {
        // Pitfall: consider behaviour of modulo with negative values (`rem` vs `mod`)
        val outcome = Outcome.OrdinalOrder[(playerMove.ordinal - opponentMove.ordinal).mod(3)]
        val score = outcome.score + playerMove.score
    }

    data class CorrectMatch(val id: Int, val opponentMove: Move, val desiredOutcome: Outcome) {
        val playerMove = Move.OrdinalOrder[((opponentMove.ordinal + desiredOutcome.ordinal).mod(3))]
        val score = desiredOutcome.score + playerMove.score
    }

    override fun solve() {
        val matches = withInput("Day02") { input -> input
            .filter { it.isNotEmpty() }
            .mapIndexed { i, line ->
                Match(
                    id = i,
                    opponentMove = Move.DeserializationOrder[line[0] - 'A'],
                    playerMove = Move.DeserializationOrder[line[2] - 'X']
                )
            }
            .toList()
        }

        val firstHalfSolution = matches.sumOf { it.score }
        println("Solution A: $firstHalfSolution")
        assert(firstHalfSolution == 13675)

        val correctMatches = withInput("Day02") { input -> input
            .filter { it.isNotEmpty() }
            .mapIndexed { i, line ->
                CorrectMatch(
                    id = i,
                    opponentMove = Move.DeserializationOrder[line[0] - 'A'],
                    desiredOutcome = Outcome.DeserializationOrder[line[2] - 'X']
                )
            }
            .toList()
        }

        val secondHalfSolution = correctMatches.sumOf { it.score }
        println("Solution B: $secondHalfSolution")
        assert(secondHalfSolution == 14184)
    }
}
