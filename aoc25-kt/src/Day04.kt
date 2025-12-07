import utils.Direction
import utils.Point
import utils.readInputToList

private const val AOC_DAY = "Day04"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private const val PAPER = '@'
private const val EMPTY = '.'

private fun checkAdjacentPositions(grid: List<List<Char>>, point: Point): Int {
    var counter = 0
    for (dir in enumValues<Direction>()) {
        val newPoint = point.add(dir.point)
        if (!newPoint.isInsideBounds(grid[0].lastIndex, grid.lastIndex)) continue

        if (grid[newPoint.y][newPoint.x] == PAPER) counter++
    }
    return counter
}

private fun part1(input: List<String>): Int {
    val grid = input.map { it.toList() }
    var counter = 0
    for (y in 0..grid.lastIndex) {
        for (x in 0..grid[0].lastIndex) {
            if (grid[y][x] == EMPTY) continue

            if (checkAdjacentPositions(grid, Point(x, y)) < 4) counter++
        }
    }
    return counter
}

private fun part2(grid: List<String>): Int {
    val mutableGrid = grid.map { line -> line.toMutableList() }

    var finalCounter = 0
    while (true) {
        var counter = 0
        for (y in 0..grid.lastIndex) {
            for (x in 0..grid[0].lastIndex) {
                if (mutableGrid[y][x] == EMPTY) continue

                if (checkAdjacentPositions(mutableGrid, Point(x, y)) < 4) {
                    counter++
                    mutableGrid[y][x] = EMPTY
                }
            }
        }
        if (counter == 0) break
        finalCounter += counter
    }

    return finalCounter
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 13
    val part2ExpectedRes = 43

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 1518" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 8665" else ""}")
}