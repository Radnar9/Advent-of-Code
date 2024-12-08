import utils.readInputToList

private const val AOC_DAY = "Day08"
private const val INPUT_FILE = AOC_DAY
private const val TEST_FILE = "${AOC_DAY}_test"

private data class Location(val row: Int, val col: Int)

private fun getAllAntennas(map: List<String>): Map<Char, List<Location>> {
    val antennas = mutableMapOf<Char, MutableList<Location>>()
    for ((rowI, row) in map.withIndex()) {
        for ((colI, freqChar) in row.withIndex()) {
            if (freqChar != '.') {
                if (!antennas.contains(freqChar)) {
                    antennas[freqChar] = mutableListOf()
                }
                antennas[freqChar]!!.add(Location(rowI, colI))
            }
        }
    }
    return antennas
}

private fun insideMapBounds(map: List<String>, location: Location): Boolean {
    return location.row in map.indices && location.col in map[0].indices
}

private fun part1(input: List<String>): Int {
    val antennas = getAllAntennas(input)

    val antinodes = mutableSetOf<Location>()
    for (antennasArr in antennas.values) {
        for (antenna1 in antennasArr) {     // Top A:   (r1, c1)
            for (antenna2 in antennasArr) { // Bottom A:(r2, c2) Diff: (r2-r1, c2-c1)
                if (antenna1 == antenna2) continue

                // (r1-(r2-r1), c1-(c2-c1)) = (2*r1-r2, 2*c1-c2)
                val topAntinode = Location(2 * antenna1.row - antenna2.row, 2 * antenna1.col - antenna2.col)
                antinodes.add(topAntinode)

                // (r2+(r2-r1), c2+(c2-c1)) = (2*r2-r1, 2*c2-c1)
                // val bottomAntinode = Location(2 * antenna2.row - antenna1.row, 2 * antenna2.col - antenna1.col)
                // antinodes.apply { add(topAntinode); add(bottomAntinode) }

                // With this loop structure we will be calculating the two equations twice, A & A', and then A' & A
                // By only having one of the equations, since we will always pass by both pairs, when we get to the
                // symmetric pair, the difference will be (r1-r2, c1-c2) which will result in calculating the result of
                // the bottom antinode equation, or the opposite if had commented the first equation.
            }
        }
    }
    return antinodes.count { antinode -> insideMapBounds(input, antinode) }
}

private fun part2(input: List<String>): Int {
    val antennas = getAllAntennas(input)

    val antinodes = mutableSetOf<Location>()
    for (antennasArr in antennas.values) {
        for (antenna1 in antennasArr) {
            for (antenna2 in antennasArr) {
                if (antenna1 == antenna2) continue
                val freqDiff = Location(antenna1.row - antenna2.row,  antenna1.col - antenna2.col)
                var possibleAntinode = antenna1
                while (insideMapBounds(input, possibleAntinode)) {
                    antinodes.add(possibleAntinode)
                    possibleAntinode = Location(possibleAntinode.row + freqDiff.row, possibleAntinode.col + freqDiff.col)
                }
            }
        }
    }
    return antinodes.count()
}

fun main() {
    val testInput = readInputToList(TEST_FILE)
    val part1ExpectedRes = 14
    val part2ExpectedRes = 34

    println("---| TEST INPUT |---")
    println("* PART 1:   ${part1(testInput)}\t== $part1ExpectedRes")
    println("* PART 2:   ${part2(testInput)}\t== $part2ExpectedRes\n")

    val finalInput = readInputToList(INPUT_FILE)
    val improving = true
    println("---| FINAL INPUT |---")
    println("* PART 1: ${part1(finalInput)}${if (improving) "\t== 214" else ""}")
    println("* PART 2: ${part2(finalInput)}${if (improving) "\t== 809" else ""}")
}