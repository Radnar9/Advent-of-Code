package utils

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file into a list of strings.
 */
fun readInputToList(fileName: String) = Path("src/$fileName.txt").readLines()

/**
 * Reads lines from the given input txt file into a single string.
 */
fun readInputToString(fileName: String) = Path("src/$fileName.txt").readText()

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)