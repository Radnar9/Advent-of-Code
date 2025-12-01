import utils.readInputToList

private const val AOC_DAY = "Day01"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private const val MAX_DIAL_NUMBERS = 100
private const val INITIAL_DIAL_POINTER = 50

private fun part1(input: List<String>): Int {
    var currentDialPointer = INITIAL_DIAL_POINTER

    val dialAt0Counter = input.count { rotation ->
        val dir = rotation[0]
        val number = rotation.substring(1).toInt()
        currentDialPointer = when (dir) {
            'L' -> (currentDialPointer - number + MAX_DIAL_NUMBERS) % MAX_DIAL_NUMBERS
            else -> (currentDialPointer + number) % MAX_DIAL_NUMBERS
        }
        currentDialPointer == 0
    }
    return dialAt0Counter
}

private fun part2(input: List<String>): Int {
    var currentDialNumber = INITIAL_DIAL_POINTER
    var counter = 0

    input.forEach { rotation ->
        val dir = rotation[0]
        val number = rotation.substring(1).toInt()

        val fullRotations = number / MAX_DIAL_NUMBERS
        counter += fullRotations

        var nextDialNumber = when (dir) {
            // Get the partial values and check if they cross the dial number 0.
            // For instance, with a current dial number of 65 and a number of 45, check if it will surpass the final dial number,
            // which in this case it will. And the same idea for the other direction.
            'L' -> {
                val res = currentDialNumber - (number % MAX_DIAL_NUMBERS)
                if (currentDialNumber != 0 && res < 0) counter++
                res
            }
            else -> {
                val res = currentDialNumber + (number % MAX_DIAL_NUMBERS)
                if (currentDialNumber != 0 && res > MAX_DIAL_NUMBERS) counter++
                res
            }
        }

        // Get the next actual dial number, if it's less than 0, we need to increment by the max number to get the corresponding position
        nextDialNumber %= MAX_DIAL_NUMBERS
        if (nextDialNumber < 0) {
            nextDialNumber += MAX_DIAL_NUMBERS
        }

        // Count the ends of the rotations, but check if the initial dial number isn't a 0,
        // otherwise we would be counting duplicates with the fullRotations variable
        if (currentDialNumber != 0 && nextDialNumber == 0) {
            counter++
        }

        currentDialNumber = nextDialNumber
    }

    return counter
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 3
    val part2ExpectedRes = 6

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 1120" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 6554" else ""}")
}