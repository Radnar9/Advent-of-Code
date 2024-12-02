import utils.dropAt
import utils.readInputToList
import kotlin.math.absoluteValue

private const val AOC_DAY = "Day02"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun isReportSafe(report: List<Int>): Boolean {
    /*val reportsDiffs = reports // reports: List<List<Int>>
        .map { it.zipWithNext() }
        .map { it.map { (first, second) -> first - second } }*/

    val reportDiffs = report.windowed(2).map { level -> level[0] - level[1] }
    return (reportDiffs.all { it > 0 } || reportDiffs.all { it < 0 }) && reportDiffs.all { it.absoluteValue in 1..3 }
}

private fun part1(input: List<String>): Int {
    val reports = input.map { line -> line.split(" ").map { it.toInt() } }
    return reports.count { report -> isReportSafe(report) }
}

private fun part2(input: List<String>): Int {
    val reports = input.map { line -> line.split(" ").map { it.toInt() } }
    return reports.count { report ->
        when {
            !isReportSafe(report) -> report.indices.any { isReportSafe(report.dropAt(it)) }
            else -> true
        }
    }
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 2
    val part2ExpectedRes = 4

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 502" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 544" else ""}")
}