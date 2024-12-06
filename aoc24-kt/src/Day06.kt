import utils.readInputToList
import utils.replaceCharAt

private const val AOC_DAY = "Day06"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private const val FREE = '.'
private const val GUARD = '^'
private const val OBSTRUCTION = '#'
private data class Position(val row: Int, val col: Int)
private enum class Direction(val row: Int, val col: Int) {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1)
}

// Checks if the guard is on the grid's edges
private fun isGuardLeaving(map: List<String>, guardPosition: Position): Boolean {
    return guardPosition.row >= map.size - 1 || guardPosition.row <= 0
            || guardPosition.col >= map[0].length - 1 || guardPosition.col <= 0
}

private fun turn90Degrees(direction: Direction): Direction {
    return when (direction) {
        Direction.UP -> Direction.RIGHT
        Direction.RIGHT -> Direction.DOWN
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }
    // or tmp = drow, drow = dcol, dcol = -drow
}

private fun getAllGuardDistinctPositions(map: List<String>, guardStartingPos: Position, guardInitialDir: Direction): List<Position> {
    var guardCurrentPos = guardStartingPos
    var guardDir = guardInitialDir

    val distinctPos = mutableSetOf<Position>(guardStartingPos)
    while (!isGuardLeaving(map, guardCurrentPos)) {
        val nextGuardPos = Position(guardCurrentPos.row + guardDir.row, guardCurrentPos.col + guardDir.col)
        if (map[nextGuardPos.row][nextGuardPos.col] == OBSTRUCTION) {
            guardDir = turn90Degrees(guardDir)
            continue
        }
        guardCurrentPos = nextGuardPos
        distinctPos.add(nextGuardPos)
    }
    return distinctPos.toList()
}

private fun isLoop(map: List<String>, guardStartingPos: Position, guardInitialDir: Direction): Boolean {
    var guardCurrentPos = guardStartingPos
    var guardDir = guardInitialDir

    // If the guard goes through the same position and the same direction twice, he is in a loop
    val distinctPos = mutableSetOf<Pair<Position, Direction>>(Pair(guardStartingPos, guardDir))
    while (!isGuardLeaving(map, guardCurrentPos)) {
        val nextGuardPos = Position(guardCurrentPos.row + guardDir.row, guardCurrentPos.col + guardDir.col)
        if (map[nextGuardPos.row][nextGuardPos.col] == OBSTRUCTION) {
            guardDir = turn90Degrees(guardDir)
            continue
        }
        guardCurrentPos = nextGuardPos
        if (!distinctPos.add(Pair(nextGuardPos, guardDir))) return true
    }
    return false
}

private fun part1(input: List<String>): Int {
    val guardStartingPos = input.indexOfFirst { it.contains(GUARD) }.let { Position(it, input[it].indexOf(GUARD)) }
    return getAllGuardDistinctPositions(input, guardStartingPos, Direction.UP).size
}

private fun part2(input: List<String>): Int {
    val guardStartingPos = input.indexOfFirst { it.contains(GUARD) }.let { Position(it, input[it].indexOf(GUARD)) }
    val possibleGuardPositions = getAllGuardDistinctPositions(input, guardStartingPos, Direction.UP)

    var loopCounter = 0
    val mutableMap = input.toMutableList()

    // Check if placing an obstruction in every free position of the guard's path leads to a loop
    for (pos in possibleGuardPositions) {
        if (mutableMap[pos.row][pos.col] != FREE) {
            continue
        }
        mutableMap[pos.row] = mutableMap[pos.row].replaceCharAt(pos.col, OBSTRUCTION)
        if (isLoop(mutableMap.toList(), guardStartingPos, Direction.UP)) {
            loopCounter++
        }
        mutableMap[pos.row] = mutableMap[pos.row].replaceCharAt(pos.col, FREE)
    }
    return loopCounter
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 41
    val part2ExpectedRes = 6

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 5329" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 2162" else ""}")
}