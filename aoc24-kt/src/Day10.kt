import utils.readInputToList

private const val AOC_DAY = "Day10"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private enum class TrailDirection(val x: Int, val y: Int) {
    LEFT(-1, 0), RIGHT(1, 0), UP(0, -1), DOWN(0, 1)
}

private data class TrailPosition(val x: Int, val y: Int) {
    override fun toString() = "($x, $y)"
}
private fun insideBounds(map: List<String>, pos: TrailPosition): Boolean {
    return pos.y in map.indices && pos.x in map[0].indices
}

private fun getPositionValue(map: List<String>, pos: TrailPosition): Char {
    return map[pos.y][pos.x]
}

private fun getHeadTrailPositions(map: List<String>): List<TrailPosition> {
    return buildList<TrailPosition> {
        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, col ->
                if (col == '0') add(TrailPosition(x, y))
            }
        }
    }
}

private fun countTrails(map: List<String>, currPos: TrailPosition, visited: MutableSet<TrailPosition>, distinctPath: Boolean): Int {
    val currValue = getPositionValue(map, currPos).digitToInt()
    if (currValue == 9) {
        if (distinctPath) {
            return if (visited.add(currPos)) 1 else 0
        }
        return 1
    }
    var counter = 0
    for (dir in TrailDirection.entries) {
        val nextPos = TrailPosition(currPos.x + dir.x, currPos.y + dir.y)
        if (!insideBounds(map, nextPos)) continue
        val nextValue = getPositionValue(map, nextPos).digitToInt()
        if (nextValue - currValue != 1) continue
        counter += countTrails(map, nextPos, visited, distinctPath)
    }
    return counter
}

private fun part1(input: List<String>): Int {
    val trailHeads = getHeadTrailPositions(input)
    return trailHeads.sumOf { countTrails(input, it, mutableSetOf(), true) }
}

private fun part2(input: List<String>): Int {
    val trailHeads = getHeadTrailPositions(input)
    return trailHeads.sumOf { countTrails(input, it, mutableSetOf(), false) }
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 36
    val part2ExpectedRes = 81

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 461" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 875" else ""}")
}