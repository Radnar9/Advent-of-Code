import utils.readInputToString

private const val AOC_DAY = "Day09"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private fun part1(input: String): Long {
    val diskMap = input.map { it.digitToInt() }
    val extendedMap = ArrayList<Int>()
    var filesCounter = 0
    for ((i, file) in diskMap.withIndex()) {
        if (i % 2 == 1) filesCounter++
        for (j in 0..<file) {
            when (i % 2) {
                0 -> extendedMap.add(filesCounter)
                1 -> extendedMap.add(-1)
            }
        }
    }
    val freeSpaces = extendedMap.indices.filter { extendedMap[it] == -1 }
    for (i in freeSpaces) {
        while (extendedMap.last == -1) extendedMap.removeLast()
        if (extendedMap.size <= i) break
        extendedMap[i] = extendedMap.removeLast()
    }
    return extendedMap.indices.sumOf { id -> (id * extendedMap[id]).toLong() }
}

private fun part2(input: String): Long {
    data class Block(val startIdx: Int, val size: Int)
    val diskMap = input.map { it.digitToInt() }
    val extendedMap = mutableMapOf<Int, Block>()
    val freeSpaces = mutableListOf<Block>()

    var filesCounter = 0
    var position = 0
    for ((i, fileSize) in diskMap.withIndex()) {
        when (i % 2) {
            0 -> extendedMap[filesCounter++] = Block(position, fileSize)
            1 -> freeSpaces.add(Block(position, fileSize))
        }
        position += fileSize
    }
    while (filesCounter > 0) {
        filesCounter--
        val fileBlock = extendedMap[filesCounter]!!
        for ((i, freeBlock) in freeSpaces.withIndex()) {
            if (freeBlock.startIdx >= fileBlock.startIdx) break
            if (fileBlock.size <= freeBlock.size) {
                extendedMap[filesCounter] = Block(freeBlock.startIdx, fileBlock.size)
                if (fileBlock.size == freeBlock.size) {
                    freeSpaces.removeAt(i)
                } else {
                    freeSpaces[i] = Block(freeBlock.startIdx + fileBlock.size, freeBlock.size - fileBlock.size)
                }
                break
            }
        }
    }
    val checksum = extendedMap.entries.sumOf { (id, block) ->
        (block.startIdx..<block.startIdx + block.size).sumOf { id.toLong() * it }
    }

    return checksum
}

fun main() {
    val testInput = readInputToString(TEST_FILE)
    val part1ExpectedRes = 1928
    val part2ExpectedRes = 2858

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToString(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 6291146824486" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 6307279963620" else ""}")
}