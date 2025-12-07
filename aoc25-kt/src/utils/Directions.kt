package utils

data class Point(val x: Int, val y: Int) {
    fun add(point: Point) = Point(point.x + x, point.y + y)
    fun isInsideBounds(xMax: Int, yMax: Int) = x in 0..xMax && y in 0..yMax
}

enum class Direction(val point: Point) {
    UP(Point(0, -1)),
    DOWN(Point(0, 1)),
    LEFT(Point(-1, 0)),
    RIGHT(Point(1, 0)),
    DIAGONAL_UP_LEFT(Point(-1, -1)),
    DIAGONAL_UP_RIGHT(Point(1, -1)),
    DIAGONAL_DOWN_LEFT(Point(-1, 1)),
    DIAGONAL_DOWN_RIGHT(Point(1, 1)),
}
