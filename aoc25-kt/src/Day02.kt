import utils.readInputToString
import kotlin.text.split

private const val AOC_DAY = "Day02"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun part1(input: String): Long {
    val ranges = input
        .split(",")
        .map { range ->
            val (rangeInitialNumber, rangeLastNumber) = range.split("-")
            (rangeInitialNumber.toLong()..rangeLastNumber.toLong())
        }

    var finalSum = 0L
    for (range in ranges) {
        for (num in range) {
            val numStr = num.toString()
            if (numStr.length % 2 == 0 && numStr.substring(0..<numStr.length/2) == numStr.substring(numStr.length/2..<numStr.length)) {
                finalSum += num
            }
        }
    }

    return finalSum
}

private fun part2(input: String): Long {
    val ranges = input
        .split(",")
        .map { range ->
            val (rangeInitialNumber, rangeLastNumber) = range.split("-")
            (rangeInitialNumber.toLong()..rangeLastNumber.toLong())
        }

    var finalSum = 0L
    for (range in ranges) {
        for (num in range) {
            val numStr = num.toString()
            for (i in 2..numStr.length) {
                if (numStr.length % i == 0 && numStr.substring(0..<numStr.length/i).repeat(i) == numStr) {
                    println(numStr)
                    finalSum += num
                    break
                }
            }
        }
    }
    return finalSum
}

fun main() {
    val testInput = readInputToString(TEST_FILE)
    val part1ExpectedRes = 1227775554
    val part2ExpectedRes = 4174379265

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToString(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 44487518055" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 53481866137" else ""}")
}