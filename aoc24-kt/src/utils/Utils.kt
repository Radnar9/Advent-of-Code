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

/**
 * Drop element at a specific index
 * @return immutable list without the removed element
 */
fun <T> List<T>.dropAt(index: Int) = this.filterIndexed { i, _ ->
    i != index
}

/**
 * Replaces a String's Char at a specific index.
 * @return the String with the replaced Char.
 */
fun String.replaceCharAt(index: Int, replacement: Char): String {
    return this.substring(0, index) + replacement + this.substring(index + 1)
}