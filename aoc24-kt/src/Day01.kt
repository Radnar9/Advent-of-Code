import utils.readInputToList
import java.util.*
import kotlin.math.abs

private const val AOC_DAY = "Day01"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun part1PQ(input: List<String>): Int {
    val leftList = PriorityQueue<Int>(input.size)
    val rightList = PriorityQueue<Int>(input.size)

    input.forEach { line ->
        val (val1, val2) = line.split(Regex("\\s+"))
        leftList.add(val1.toInt())
        rightList.add(val2.toInt())
    }

    var totalDistance = 0
    for (i in leftList.indices) {
        totalDistance += abs(leftList.poll() - rightList.poll())
    }

    return totalDistance
}

private fun part1(input: List<String>): Int {
    val (leftList, rightList) = input.map { line ->
        val (leftVal, rightVal) = line.split(Regex("\\s+"))
        leftVal.toInt() to rightVal.toInt()
    }.unzip()

    val totalDistance = leftList.sorted().zip(rightList.sorted()).sumOf { (left, right) ->
        abs(left - right)
    }

    return totalDistance
}

private fun part2(input: List<String>): Int {
    val (leftList, rightList) = input.map { line ->
        val (leftVal, rightVal) = line.split(Regex("\\s+"))
        leftVal.toInt() to rightVal.toInt()
    }.unzip()

    // occurrencesMap[rightVal] = occurrencesMap.getOrDefault(rightVal, 0) + 1
    val occurrencesMap = rightList.groupingBy { it }.eachCount()

    val similarityScore = leftList.sumOf { leftValue ->
        leftValue * (occurrencesMap[leftValue] ?: 0) // same as getOrDefault(leftValue, 0)
    }

    /*val similarityScore = leftList.fold(0) { acc, leftValue ->
        acc + leftValue * (occurrencesMap[leftValue] ?: 0)
    }*/

    return similarityScore
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 11
    val part2ExpectedRes = 31

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 1388114" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 23529853" else ""}")
}