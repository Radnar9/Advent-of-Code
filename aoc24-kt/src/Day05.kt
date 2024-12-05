import utils.readInputToList

private const val AOC_DAY = "Day05"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun isOrdered(rulesMap: Map<String, Set<String>>, update: List<String>): Boolean {
    for ((i, iNum) in update.withIndex()) {
        for (j in i + 1..<update.size) {
            val jNum = update[j]
            // Keys are the bosses, the values are the workers who must come after the boss
            if (rulesMap[jNum] != null && rulesMap[jNum]!!.contains(iNum)) {
                return false
            }
        }
    }
    return true
}

private fun part1(input: List<String>): Int {
    val sectionSeparatorIdx = input.indexOf("")
    val rules = input.subList(0, sectionSeparatorIdx).map { rule -> rule.split("|") }
    val updates = input.subList(sectionSeparatorIdx + 1, input.size).map { update -> update.split(",") }

    // Keys are the first numbers, values are the second numbers
    val rulesMap = mutableMapOf<String, MutableSet<String>>().apply {
        rules.forEach { (rule1, rule2) -> put(rule1, getOrDefault(rule1, mutableSetOf()).apply { add(rule2) }) }
    }

    var counter = 0
    for (update in updates) {
        if (!isOrdered(rulesMap, update)) continue
        val middlePageNumber = update[update.size / 2].toInt()
        counter += middlePageNumber
    }

    return counter
}

private fun part2(input: List<String>): Int {
    val sectionSeparatorIdx = input.indexOf("")
    val rules = input.subList(0, sectionSeparatorIdx).map { rule -> rule.split("|") }
    val updates = input.subList(sectionSeparatorIdx + 1, input.size).map { update -> update.split(",") }

    // Keys are the first numbers, values are the second numbers
    val rulesMap = mutableMapOf<String, MutableSet<String>>().apply {
        rules.forEach { (rule1, rule2) -> put(rule1, getOrDefault(rule1, mutableSetOf()).apply { add(rule2) }) }
    }

    var counter = 0
    for (update in updates) {
        if (isOrdered(rulesMap, update)) continue
        val sortedUpdate = update.sortedWith { o1, o2 ->
            when {
                rulesMap[o1]?.contains(o2) == true -> -1 // o1 is o2's boss, then o1 will appear before o2
                rulesMap[o2]?.contains(o1) == true -> 1
                else -> 0
            }
        }
        val middlePageNumber = sortedUpdate[sortedUpdate.size / 2].toInt()
        counter += middlePageNumber
    }

    return counter
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 143
    val part2ExpectedRes = 123

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 5639" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 5273" else ""}")
}