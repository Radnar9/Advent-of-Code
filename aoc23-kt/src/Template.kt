private const val AOC_DAY = "Day??"// TODO: Change day!
private const val TEST_FILE = "${AOC_DAY}_test"
private const val PART1_TEST_FILE = "${AOC_DAY}_test_part1"
private const val PART2_TEST_FILE = "${AOC_DAY}_test_part2"
private const val INPUT_FILE = AOC_DAY



private fun part1(input: List<String>): Int {
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}



fun main() {
    createTestFiles(AOC_DAY)

//    val testInput = readInputToList(TEST_FILE)
    val part1TestInput = readInputToList(PART1_TEST_FILE)
    val part1ExpectedRes = -1
    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(part1TestInput)}\t== $part1ExpectedRes")

    val part2TestInput = readInputToList(PART2_TEST_FILE)
    val part2ExpectedRes = -1
    println("* PART 2:   ${part2(part2TestInput)}\t== $part2ExpectedRes\n")


    val input = readInputToList(INPUT_FILE)
    val improving = false
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(input)}${if (improving) "\t== ???" else ""}")
    println("* PART 2: ${part2(input)}${if (improving) "\t== ???" else ""}")
}
