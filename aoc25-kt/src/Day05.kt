import utils.readInputToList

private const val AOC_DAY = "Day05"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun part1(input: List<String>): Int {
    val breakpointIndex = input.indexOf("")
    val ranges = input.take(breakpointIndex).map { range ->
        val (start, end) = range.split("-")
        start.toLong()..end.toLong()
    }
    val ingredients = input.drop(breakpointIndex + 1).map { it.toLong() }

    var counter = 0
    for (ingredient in ingredients) {
        for (range in ranges) {
            if (ingredient in range) {
                counter++
                break
            }
        }
    }

    return counter
}

private fun part2(input: List<String>): Long {
    val ranges = input.take(input.indexOf("")).map { range ->
        val (start, end) = range.split("-")
        start.toLong()..end.toLong()
    }.sortedWith(compareBy<LongRange> { it.first }.thenBy { it.last })

    var counter = 0L
    var previousRange = ranges.first()
    for (range in ranges) {
        if (range == ranges.first()) continue

        // Ranges don't overlap
        if (previousRange.last < range.first) {
            counter += previousRange.last - previousRange.first + 1
            previousRange = range
        } else {
            previousRange = previousRange.first..maxOf(previousRange.last, range.last)
        }
    }
    counter += previousRange.last - previousRange.first + 1
    return counter
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 3
    val part2ExpectedRes = 14

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 607" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 342433357244012" else ""}")
}