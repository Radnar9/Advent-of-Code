package utils

enum class Direction(val row: Int, val col: Int) {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

    fun rotate(dir: Direction): Direction {
        return when {
            this == UP && dir == LEFT -> LEFT
            this == UP && dir == RIGHT -> RIGHT
            this == DOWN && dir == LEFT -> RIGHT
            this == DOWN && dir == RIGHT -> LEFT
            this == LEFT && dir == LEFT -> DOWN
            this == LEFT && dir == RIGHT -> UP
            this == RIGHT && dir == LEFT -> UP
            this == RIGHT && dir == RIGHT -> DOWN
            else -> throw Exception("A rotation possibility is missing!")
        }
    }
}

data class Point(val row: Int, val col: Int) {
    fun move(direction: Direction): Point {
        return Point(row + direction.row, col + direction.col)
    }

    fun getNeighbors(): List<Point> {
        return buildList {
            for (dir in Direction.entries) {
                add(Point(row + dir.row, col + dir.col))
            }
        }
    }
}

fun List<String>.toGridMap(): HashMap<Point, Char> {
    val gridMap = HashMap<Point, Char>()

    for ((r, line) in this.withIndex()) {
        for ((c, value) in line.withIndex()) {
            val point = Point(r, c)
            gridMap[point] = value
        }
    }

    return gridMap
}