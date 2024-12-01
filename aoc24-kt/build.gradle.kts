plugins {
    kotlin("jvm") version "2.1.0"
}

group = "radnar"
version = "1.0"

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

kotlin {
    jvmToolchain(21)
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }

    task("generateNextDay") {
        doLast {
            val prevDayNum = fileTree("$projectDir/src").matching {
                include("Day*.kt")
            }.maxOf {
                val (prevDayNum) = Regex("Day(\\d\\d)").find(it.name)!!.destructured
                prevDayNum.toInt()
            }
            val newDayNum = String.format("%02d", prevDayNum + 1)
            File("$projectDir/src", "Day$newDayNum.kt").writeText(
                """
                    >import utils.readInputToList

                    >private const val AOC_DAY = "Day$newDayNum"
                    >private const val INPUT_FILE = AOC_DAY
                    >private const val TEST_FILE = "$+{AOC_DAY}_test"

                    >private fun part1(input: List<String>): Int {
                    >    return Int.MAX_VALUE
                    >}

                    >private fun part2(input: List<String>): Int {
                    >    return Int.MAX_VALUE
                    >}

                    >fun main() {
                    >    val testInput = readInputToList(TEST_FILE)
                    >    val part1ExpectedRes = Int.MAX_VALUE
                    >    val part2ExpectedRes = Int.MAX_VALUE
                    >
                    >    println("---| TEST INPUT |---")
                    >    println("* PART 1:   $+{part1(testInput)}\t== $+part1ExpectedRes")
                    >    println("* PART 2:   $+{part2(testInput)}\t== $+part2ExpectedRes\n")
                    >
                    >    val finalInput = readInputToList(INPUT_FILE)
                    >    val improving = false
                    >    println("---| FINAL INPUT |---")
                    >    println("* PART 1: $+{part1(finalInput)}$+{if (improving) "\t== ???" else ""}")
                    >    println("* PART 2: $+{part2(finalInput)}$+{if (improving) "\t== ???" else ""}")
                    >}
                """.replace("+", "")
                    .trimMargin(">")
            )
            File("$projectDir/src", "Day$newDayNum.txt").createNewFile()
            File("$projectDir/src", "Day${newDayNum}_test.txt").createNewFile()
            File("$projectDir/src", "Day${newDayNum}_test_part1.txt").createNewFile()
            File("$projectDir/src", "Day${newDayNum}_test_part2.txt").createNewFile()
        }
    }
}
