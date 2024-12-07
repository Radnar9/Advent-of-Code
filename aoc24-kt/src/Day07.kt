import utils.readInputToList
import Operator.*

private const val AOC_DAY = "Day07"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private enum class Operator { ADD, MUL, CONCAT }
private fun isTrueEquation(testValue: Long, numbers: List<Long>, operators: List<Operator>, sum: Long, index: Int): Boolean {
    if (index >= numbers.size) {
        return sum == testValue
    }
    return operators.any { operator ->
        val currentNumber = numbers[index]
        val currentSum = when (operator) {
            ADD -> sum + currentNumber
            MUL -> sum * currentNumber
            CONCAT -> "$sum$currentNumber".toLong()
        }
        isTrueEquation(testValue, numbers, operators, currentSum, index + 1)
    }
}

private fun part1(input: List<String>): Long {
    val numbersRegex = """(\d+)""".toRegex()
    val equations = input.map { line -> numbersRegex.findAll(line).map { num -> num.value.toLong() }.toList() }

    val totalCalibrationResult = equations.filter { equation ->
        val testValue = equation.first()
        val numbers = equation.drop(1)
        isTrueEquation(testValue, numbers, listOf(ADD, MUL), numbers.first(), 1)
    }.sumOf { it.first() }

    return totalCalibrationResult
}

private fun part2(input: List<String>): Long {
    val numbersRegex = """(\d+)""".toRegex()
    val equations = input.map { line -> numbersRegex.findAll(line).map { num -> num.value.toLong() }.toList() }

    val totalCalibrationResult = equations.filter { equation ->
        val testValue = equation.first()
        val numbers = equation.drop(1)
        isTrueEquation(testValue, numbers, listOf(ADD, MUL, CONCAT), numbers.first(), 1)
    }.sumOf { it.first() }

    return totalCalibrationResult
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 3749
    val part2ExpectedRes = 11387

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 3245122495150" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 105517128211543" else ""}")
}