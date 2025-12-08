import utils.readInputToList

private const val AOC_DAY = "Day06"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun part1(input: List<String>): Long {
    val numbers = input.take(input.size - 1)
        .map {
            it.split(Regex("\\s+"))
                .filterNot { elem -> elem.isEmpty() } // Since there are elements that have a space at the beginning of the line like: "  1 2 3"
                .map { number -> number.toInt() }
        }
    val operations = input.drop(input.size - 1).flatMap { it.split(Regex("\\s+")) }

    var total = 0L
    for (col in numbers[0].indices) {
        val operation = operations[col]
        var counter = if (operation == "*") 1L else 0L
        for (line in numbers.indices) {
            when (operation) {
                "+" -> counter += numbers[line][col]
                "*" -> counter *= numbers[line][col]
            }
        }
        total += counter
    }

    return total
}

private fun part2(input: List<String>): Long {
    val operations = input.drop(input.size - 1).flatMap { it.split(Regex("\\s+")) }
    val numbersInput = input.take(input.size - 1)

    // There can be strings larger than the others
    val longestString = numbersInput.maxBy { it.length }

    var operationIdx = 0
    var total = 0L
    var currentTotal = if (operations[operationIdx] == "*") 1 else 0L

    for (col in longestString.indices) {
        var currentNumber = ""
        for (line in numbersInput.indices) {
            // If the current line doesn't have the largest length
            if (numbersInput[line].lastIndex < col) continue

            val digit = numbersInput[line][col]
            if (!digit.isDigit()) continue

            currentNumber += digit
        }

        // If the currentNumber remains empty, means that we are moving to a different operation set
        if (currentNumber.isEmpty()) {
            total += currentTotal
            operationIdx += if (operations.lastIndex == operationIdx) 0 else 1
            currentTotal = if (operations[operationIdx] == "*") 1 else 0
            continue
        }

        when (operations[operationIdx]) {
            "+" -> currentTotal += currentNumber.toInt()
            "*" -> currentTotal *= currentNumber.toInt()
        }
    }

    total += currentTotal
    return total
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 4277556
    val part2ExpectedRes = 3263827

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 6891729672676" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 9770311947567" else ""}")
}