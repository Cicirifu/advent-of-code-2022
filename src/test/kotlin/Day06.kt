import kotlin.test.assertEquals

class Day06 {
    private fun Sequence<*>.findStartMarker(windowSize: Int) = this
        .windowed(size = windowSize, step = 1).withIndex()
        .filter { (_, window) -> window.toSet().size == windowSize }
        .firstOrNull()?.let { (index, code) -> (windowSize + index) to code }

    @Puzzle
    fun solve() {
        val input = {
            readInput("Day06").asSequence()
                .flatMap { it.asSequence() }
        }

        val (firstHalfSolution, startPacketCode) = input().findStartMarker(4)!!
        println("Solution A: $firstHalfSolution (${startPacketCode})")
        assertEquals(1766, firstHalfSolution)

        val (secondHalfSolution, startMessageCode) = input().findStartMarker(14)!!
        println("Solution B: $secondHalfSolution (${startMessageCode})")
        assertEquals(2383, secondHalfSolution)
    }
}
