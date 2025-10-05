import utils.Direction
import utils.Point
import utils.readInputToList
import utils.toGridMap
import kotlin.system.measureTimeMillis

private const val AOC_DAY = "Day12"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private data class PlantPosition(val row: Int, val col: Int)
private enum class PlantDirection(val row: Int, val col: Int) {
    LEFT(0, -1), UP(-1, 0), DOWN(1, 0), RIGHT(0, 1)
}
private fun isInsideBoundaries(pos: PlantPosition, gridWidth: Int, gridHeight: Int): Boolean {
    return pos.col in (0 until gridWidth) && pos.row in (0 until gridHeight)
}

private fun part1(plots: List<String>): Int {
    val visitedPositions = hashSetOf<PlantPosition>()

    fun fetchPlantType(pos: PlantPosition, plantType: Char): Int {
        var perimeter = 0
        for (dir in PlantDirection.entries) {
            val newPos = PlantPosition(pos.row + dir.row, pos.col + dir.col)
            if (newPos in visitedPositions && plots[newPos.row][newPos.col] == plantType) continue

            if (!isInsideBoundaries(newPos, plots.first().length, plots.size)) {
                perimeter++
                continue
            }
            if (plots[newPos.row][newPos.col] != plantType) {
                perimeter++
                continue
            }
            visitedPositions.add(newPos)
            perimeter += fetchPlantType(newPos, plantType)
        }
        return perimeter
    }

    var total = 0
    for ((r, line) in plots.withIndex()) {
        for ((c, plant) in line.withIndex()) {
            val pos = PlantPosition(r, c)
            if (pos in visitedPositions) continue

            val previousVisited = visitedPositions.size
            visitedPositions.add(pos)
            val perimeter = fetchPlantType(pos, plant)

            total += perimeter * (visitedPositions.size - previousVisited)
        }
    }

    return total
}

private fun part1V2(input: List<String>): Int {
    val gridMap = input.toGridMap()
    val regionMapping = mutableMapOf<Point, Int>() // Point(row, col), PlantId
    var regionId = -1
    for ((point, plant) in gridMap) {
        if (point in regionMapping) continue
        regionId++
        val toVisit = mutableListOf(point)
        while (toVisit.isNotEmpty()) {
            val currentPoint = toVisit.removeFirst()
            if (gridMap[currentPoint] == plant) {
                regionMapping[currentPoint] = regionId
                toVisit += currentPoint.getNeighbors().filter { it !in toVisit && it !in regionMapping }
            }
        }
    }

    val total = (0..regionId).sumOf { id ->
        val points = regionMapping.filter { it.value == id }.keys
        val area = points.size
        val perimeter = points.sumOf { point ->
            4 - point.getNeighbors().count { it in points }
        }
        area * perimeter
    }

    return total
}

private fun part2(plots: List<String>): Int {
    val visitedPositions = hashSetOf<PlantPosition>()

    fun fetchPlantType(pos: PlantPosition, plantType: Char): Int {
        var perimeter = 0
        for (dir in PlantDirection.entries) {
            val newPos = PlantPosition(pos.row + dir.row, pos.col + dir.col)
            if (newPos in visitedPositions && plots[newPos.row][newPos.col] == plantType) continue

            // To check if the position behind was in the same situation. If it was, means it was already counted.
            val (invertedPosBackward, invertedPosForward) = when (dir) {
                PlantDirection.UP, PlantDirection.DOWN -> Pair(PlantPosition(pos.row, pos.col - 1), PlantPosition(pos.row, pos.col + 1))
                PlantDirection.LEFT, PlantDirection.RIGHT -> Pair(PlantPosition(pos.row - 1, pos.col), PlantPosition(pos.row + 1, pos.col))
            }
            val (invertedNewPosBackward, invertedNewPosForward) = Pair(
                PlantPosition(invertedPosBackward.row + dir.row, invertedPosBackward.col + dir.col),
            PlantPosition(invertedPosForward.row + dir.row, invertedPosForward.col + dir.col)
            )

            if (!isInsideBoundaries(newPos, plots.first().length, plots.size)) {
                // If the position behind this one wasn't also inside boundaries, it was already counted
                if (invertedPosBackward in visitedPositions && plots[invertedPosBackward.row][invertedPosBackward.col] == plantType && !isInsideBoundaries(invertedNewPosBackward,plots.first().length, plots.size)) {
                    continue
                } else if (dir == PlantDirection.LEFT && invertedPosForward in visitedPositions && plots[invertedPosForward.row][invertedPosForward.col] == plantType && !isInsideBoundaries(invertedNewPosForward,plots.first().length, plots.size)) {
                    continue
                }
                perimeter++
                continue
            }
            if (plots[newPos.row][newPos.col] != plantType) {
                // If the position behind this one wasn't also of the same type, it was already counted
                if (invertedPosBackward in visitedPositions && plots[invertedPosBackward.row][invertedPosBackward.col] == plantType && plots[invertedNewPosBackward.row][invertedNewPosBackward.col] != plantType) {
                    continue
                } else if (dir == PlantDirection.LEFT && invertedPosForward in visitedPositions && plots[invertedPosForward.row][invertedPosForward.col] == plantType && plots[invertedNewPosForward.row][invertedNewPosForward.col] != plantType) {
                    continue
                }
                perimeter++
                continue
            }
            visitedPositions.add(newPos)
            perimeter += fetchPlantType(newPos, plantType)
        }
        return perimeter
    }

    var total = 0
    for ((r, line) in plots.withIndex()) {
        for ((c, plant) in line.withIndex()) {
            val pos = PlantPosition(r, c)
            if (pos in visitedPositions) continue

            val previousVisited = visitedPositions.size
            visitedPositions.add(pos)

            val perimeter = fetchPlantType(pos, plant)
            total += perimeter * (visitedPositions.size - previousVisited)
        }
    }

    return total
}

private fun part2V2(input: List<String>): Int {
    val gridMap = input.toGridMap()
    val regionMapping = mutableMapOf<Point, Int>() // Point(row, col), PlantId
    var regionId = -1
    for ((point, plant) in gridMap) {
        if (point in regionMapping) continue
        regionId++
        val toVisit = mutableListOf(point)
        while (toVisit.isNotEmpty()) {
            val currentPoint = toVisit.removeFirst()
            if (gridMap[currentPoint] == plant) {
                regionMapping[currentPoint] = regionId
                toVisit += currentPoint.getNeighbors().filter { it !in toVisit && it !in regionMapping }
            }
        }
    }

    val total = (0..regionId).sumOf { id ->
        val points = regionMapping.filter { it.value == id }.keys
        val area = points.size
        val allBorders = Direction.entries.sumOf { dir ->
            var borderCount = 0
            val visitedPoints = mutableSetOf<Point>()
            for (point in points) {
                if (point in visitedPoints) continue
                val isBorder = regionMapping[point.move(dir)] != id
                if (isBorder) {
                    borderCount++
                    visitedPoints += point
                    listOf(Direction.LEFT, Direction.RIGHT).map { dir.rotate(it) }.forEach { sideDirection ->
                        var currentPoint = point
                        do {
                            currentPoint = currentPoint.move(sideDirection)
                            visitedPoints += currentPoint
                        } while (regionMapping[currentPoint] == id && regionMapping[currentPoint.move(dir)] != id)
                    }
                }
            }
            borderCount
        }
        area * allBorders
    }

    return total
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 140
    val part2ExpectedRes = 80

    println("---| TEST INPUT |---")
    println("* PART 1 v1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 1 v2:   ${part1V2(testInput)}\t== $part1ExpectedRes")

    println("* PART 2 v1:   ${part2(testInput)}\t== $part2ExpectedRes")
    println("* PART 2 v2:   ${part2V2(testInput)}\t== $part2ExpectedRes")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("\n---| FINAL INPUT |---")
    val p1v1 = measureTimeMillis {
        println("* PART 1 v1: ${part1(finalInput)}${if (improving) "\t== 1415378" else ""}")
    }
    println("\tTime: $p1v1 ms")
    val p1v2 = measureTimeMillis {
        println("* PART 1 v2: ${part1V2(finalInput)}${if (improving) "\t== 1415378" else ""}")
    }
    println("\tTime: $p1v2 ms\n")


    val p2v1 = measureTimeMillis {
        println("* PART 2 v1: ${part2(finalInput)}${if (improving) "\t== 862714" else ""}")
    }
    println("\tTime: $p2v1 ms")
    val p2v2 = measureTimeMillis {
        println("* PART 2 v2: ${part2V2(finalInput)}${if (improving) "\t== 862714" else ""}")
    }
    println("\tTime: $p2v2 ms")

}
