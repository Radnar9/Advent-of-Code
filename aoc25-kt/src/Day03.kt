import utils.readInputToList

private const val AOC_DAY = "Day03"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun part1(input: List<String>): Int {
    val banks = input.map { bank -> bank.map { battery -> battery.digitToInt() } }

    return banks.sumOf { bank ->
        var largestLeft = 0
        var largestRight = 0
        bank.forEachIndexed { i, battery ->
            if (battery > largestLeft && i < bank.lastIndex) {
                largestLeft = battery
                largestRight = 0
            } else if (battery > largestRight) {
                largestRight = battery
            }
        }
        largestLeft * 10 + largestRight
    }
}

private fun part2(input: List<String>): Long {
    return input.sumOf { bank ->
        val currentJoltage = Array(12) { '0' }
        bank.forEachIndexed { i, battery ->
            var reset = false
            currentJoltage.forEachIndexed { j, savedBattery ->
                if (reset) {
                    currentJoltage[j] = '0'
                } else if (battery > savedBattery && bank.lastIndex - i >= currentJoltage.lastIndex - j) {
                    currentJoltage[j] = battery
                    reset = true
                }
            }
        }
        currentJoltage.joinToString("").toLong()
    }
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 357
    val part2ExpectedRes = 3121910778619

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 17554" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 175053592950232" else ""}")
}