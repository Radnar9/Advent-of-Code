private const val AOC_DAY = "Day24"
private const val TEST_FILE = "${AOC_DAY}_test"
private const val INPUT_FILE = AOC_DAY

data class Hailstone(
    val sx: Long,
    val sy: Long,
    val sz: Long,
    val vx: Long,
    val vy: Long,
    val vz: Long,
    val a: Long = vy,
    val b: Long = -vx,
    val c: Long = vy * sx - vx * sy
) {
    constructor(hailstone: List<String>) : this(
        hailstone[0].toLong(),
        hailstone[1].toLong(),
        hailstone[2].toLong(),
        hailstone[3].toLong(),
        hailstone[4].toLong(),
        hailstone[5].toLong()
    )
}

private fun part1(input: List<String>, intersectionRange: ClosedFloatingPointRange<Double>): Long {
    val hailstones = input.map { line -> Hailstone(line.replace("@", ",").replace(" ", "").split(",")) }
    var total = 0L

    for ((i, hs1) in hailstones.withIndex()) {
        for (hs2 in hailstones.subList(0, i)) {
            // If it's parallel, skip
            if (hs1.a * hs2.b == hs1.b * hs2.a) continue

            val x = (hs1.c * hs2.b - hs2.c * hs1.b) / (hs1.a * hs2.b - hs2.a * hs1.b).toDouble()
            val y = (hs2.c * hs1.a - hs1.c * hs2.a) / (hs1.a * hs2.b - hs2.a * hs1.b).toDouble()
            if (x in intersectionRange && y in intersectionRange) {
                if (listOf(hs1, hs2).all { hs -> (x - hs.sx) * hs.vx >= 0 && (y - hs.sy) * hs.vy >= 0 }) {
                    total++
                }
            }
        }
    }
    return total
}

private fun part2(input: List<String>): Int {
    return 0
}



fun main() {
//    createTestFiles(AOC_DAY)

    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 2
    println("---| TEST INPUT |---")
//    println("* PART 1:   ${part1(testInput, 7.0..27.0)}\t== $part1ExpectedRes")

//    val part2ExpectedRes = -1
//    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")


    val input = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(input, 200000000000000.0..400000000000000.0)}${if (improving) "\t== 15318" else ""}")
//    println("* PART 2: ${part2(input)}${if (improving) "\t== ???" else ""}")
}
