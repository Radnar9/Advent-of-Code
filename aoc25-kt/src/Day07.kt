import utils.Point
import utils.readInputToList
import java.util.LinkedList

private const val AOC_DAY = "Day07"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private const val INITIAL_POINT_CHAR = 'S'
private const val SPLITTER_CHAR = '^'
private const val EMPTY_CHAR = '.'


private fun part1(grid: List<String>): Int {
    val initialPoint = Point(grid[0].indexOfFirst { it == INITIAL_POINT_CHAR }, 0)
    val seen = hashSetOf(initialPoint)
    val beamsQueue = LinkedList<Point>().apply { add(initialPoint) }
    var splittersCounter = 0

    fun addBeam(point: Point) {
        if (point in seen) return
        seen.add(point)
        beamsQueue.push(point)
    }

    while (beamsQueue.isNotEmpty()) {
        val point = beamsQueue.pop()
        val currentChar = grid[point.y][point.x]
        if (currentChar == EMPTY_CHAR || currentChar == INITIAL_POINT_CHAR) {
            if (point.y == grid.lastIndex) continue
            addBeam(Point(point.x, point.y + 1))
        }
        if (currentChar == SPLITTER_CHAR) {
            splittersCounter++
            addBeam(Point(point.x - 1, point.y))
            addBeam(Point(point.x + 1, point.y))
        }
    }

    return splittersCounter
}

private fun part2(grid: List<String>): Long {
    val cache = hashMapOf<Point, Long>()

    fun backtracking(point: Point): Long {
        if (point in cache) return cache[point]!!
        if (point.y == grid.lastIndex) return 1

        val timelinesCounter =
            when (grid[point.y][point.x]) {
                EMPTY_CHAR, INITIAL_POINT_CHAR -> backtracking(Point(point.x, point.y + 1))
                SPLITTER_CHAR -> backtracking(Point(point.x - 1, point.y)) + backtracking(Point(point.x + 1, point.y))
                else -> 0
            }

        cache[point] = timelinesCounter
        return timelinesCounter
    }

    val initialPoint = Point(grid[0].indexOfFirst { it == INITIAL_POINT_CHAR }, 0)
    return backtracking(initialPoint)
}


fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 21
    val part2ExpectedRes = 40

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 1633" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 34339203133559" else ""}")
}