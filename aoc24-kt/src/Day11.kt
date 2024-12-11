import utils.readInputToString

private const val AOC_DAY = "Day11"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun part1(input: String, blinks: Int): Int {
    val stones = ArrayList<String>().apply { addAll(input.split(" ")) }
    repeat(blinks) {
        var i = 0
        while (i in stones.indices) {
            val stone = stones[i]
            when {
                stone == "0" -> stones[i] = "1"
                stone.length % 2 == 0 -> {
                    val (leftStone, rightStone) = stone.take(stone.length / 2) to stone.takeLast(stone.length / 2)
                    stones[i] = leftStone
                    stones.add(++i, rightStone.toLong().toString())
                }
                else -> stones[i] = "${stone.toLong() * 2024}"
            }
            i++
        }
    }
    return stones.size
}

private fun countStones(stone: String, cache: Map<Int, MutableMap<String, Long>>, iter: Int): Long {
    if (iter == 0) return 1
    val iterationCache = cache[iter]!!
    val stonesCounter = iterationCache.getOrPut(stone) {
        when {
            stone == "0" -> countStones("1", cache, iter - 1)
            stone.length % 2 == 0 -> {
                val (leftStone, rightStone) = stone.take(stone.length / 2) to stone.takeLast(stone.length / 2)
                countStones(leftStone, cache, iter - 1) + countStones(rightStone.toLong().toString(), cache, iter - 1)
            }
            else -> countStones("${stone.toLong() * 2024}", cache, iter - 1)
        }
    }
    return stonesCounter
}

private fun part2(input: String, blinks: Int): Long {
    val stones = ArrayList<String>().apply { addAll(input.split(" ")) }
    val cache = (1..blinks).associateWith { mutableMapOf<String, Long>() }
    return stones.sumOf { countStones(it, cache, blinks) }
}

fun main() {
    val testInput = readInputToString(TEST_FILE)
    val part1ExpectedRes = 55312

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput, 25)}\t== $part1ExpectedRes\n")

    val finalInput = readInputToString(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput, 25)}${if (improving) "\t== 200446" else ""}")
    println("* PART 2: ${part2(finalInput, 75)}${if (improving) "\t== 238317474993392" else ""}")
}