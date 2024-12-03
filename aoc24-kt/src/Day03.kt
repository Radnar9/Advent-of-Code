import utils.readInputToString

private const val AOC_DAY = "Day03"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun part1(input: String): Int {
    val mulInstructions = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        .findAll(input)
        .map {
            val (x, y) = it.destructured      // val (_, x, y) = it.groupValues
            x.toInt() * y.toInt()
        }
    return mulInstructions.sum()
}

private fun part2(input: String): Int {
    var enabled = true
    return Regex("""mul\((\d{1,3}),(\d{1,3})\)|do(?:n't)?\(\)""") // or: ...|don't\(\)|do\(\)
        .findAll(input)
        .sumOf { match ->
            when (match.value) {
                "do()" -> enabled = true
                "don't()" -> enabled = false
            }
            if (enabled && match.value.startsWith("mul")) {
                val (x, y) = match.destructured
                x.toInt() * y.toInt()
            } else {
                0
            }
        }
}

fun main() {
    val testInput = readInputToString(TEST_FILE)
    val part1ExpectedRes = 161
    val part2ExpectedRes = 48

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToString(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 182780583" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 90772405" else ""}")
}