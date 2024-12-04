import utils.readInputToList

private const val AOC_DAY = "Day04"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private const val XMAS = "XMAS"
private const val XMAS_REVERSED = "SAMX"
private const val XMAS_LEN = XMAS.length
private const val MAS = "MAS"
private const val MAS_REVERSED = "SAM"
private const val MAS_LEN = MAS.length

private fun part1(input: List<String>): Int {
    // Verify all the possible positions: horizontal, vertical, diagonal
    // The word can be backwards and overlapping with other words
    // By checking all the positions as we go, we don't need to check upwards since we already checked those positions
    var counter = 0
    for (r in input.indices) {
        for (c in input[0].indices) {
            // Horizontal
            if (c + XMAS_LEN <= input[0].length) {
                val hCurrWord = "${input[r][c]}${input[r][c + 1]}${input[r][c + 2]}${input[r][c + 3]}"
                if (hCurrWord == XMAS || hCurrWord == XMAS_REVERSED) {
                    counter++
                }
            }

            // Vertical
            if (r + XMAS_LEN <= input.size) {
                val vCurrWord = "${input[r][c]}${input[r + 1][c]}${input[r + 2][c]}${input[r + 3][c]}"
                if (vCurrWord == XMAS || vCurrWord == XMAS_REVERSED) {
                    counter++
                }
            }

            // Diagonal -> right: x . .
            //                    . x .
            //                    . . x
            if (r + XMAS_LEN <= input.size && c + XMAS_LEN <= input[0].length) {
                val drCurrWord = "${input[r][c]}${input[r + 1][c + 1]}${input[r + 2][c + 2]}${input[r + 3][c + 3]}"
                if (drCurrWord == XMAS || drCurrWord == XMAS_REVERSED) {
                    counter++
                }
            }

            // Diagonal <- left: . . x
            //                   . x .
            //                   x . .
            if (r + XMAS_LEN <= input.size && c + 1 - XMAS_LEN >= 0) {
                val dlCurrWord = "${input[r][c]}${input[r + 1][c - 1]}${input[r + 2][c - 2]}${input[r + 3][c - 3]}"
                if (dlCurrWord == XMAS || dlCurrWord == XMAS_REVERSED) {
                    counter++
                }
            }
        }
    }
    return counter
}

private fun part2(input: List<String>): Int {
    var counter = 0
    for (r in input.indices) {
        for (c in input[0].indices) {
            // Diagonal -> right
            if (r + MAS_LEN <= input.size && c + MAS_LEN <= input[0].length) {
                val drCurrWord = "${input[r][c]}${input[r + 1][c + 1]}${input[r + 2][c + 2]}"
                if (drCurrWord == MAS || drCurrWord == MAS_REVERSED) {
                    // Diagonal <- left: starts two indexes after since we want to find a possible cross
                    val dlCurrWord = "${input[r][c + 2]}${input[r + 1][c + 1]}${input[r + 2][c]}"
                    if (dlCurrWord == MAS || dlCurrWord == MAS_REVERSED) {
                        counter++
                    }
                }
            }
        }
    }
    return counter
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 18
    val part2ExpectedRes = 9

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 2358" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 1737" else ""}")
}