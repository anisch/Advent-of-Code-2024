import Day10.part1
import Day10.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    val input = readInput("Day10")
    println(part1(input))

    check(part2(testInput) == 81)
    println(part2(input))
}

private fun Vec.inRange(map: List<List<*>>): Boolean {
    return (y in 0..map.lastIndex) && (x in 0..map[0].lastIndex)
}

private fun List<List<Int>>.trail(v: Vec, action: (Vec) -> Unit) {
    if (this[v.y][v.x] == 9) action(v)

    if (Vec(v.y, v.x + 1).inRange(this) && this[v.y][v.x] + 1 == this[v.y][v.x + 1]) {
        trail(Vec(v.y, v.x + 1), action)
    }
    if (Vec(v.y, v.x - 1).inRange(this) && this[v.y][v.x] + 1 == this[v.y][v.x - 1]) {
        trail(Vec(v.y, v.x - 1), action)
    }
    if (Vec(v.y + 1, v.x).inRange(this) && this[v.y][v.x] + 1 == this[v.y + 1][v.x]) {
        trail(Vec(v.y + 1, v.x), action)
    }
    if (Vec(v.y - 1, v.x).inRange(this) && this[v.y][v.x] + 1 == this[v.y - 1][v.x]) {
        trail(Vec(v.y - 1, v.x), action)
    }
}

object Day10 {
    fun part1(input: List<String>): Int {
        val trails = mutableSetOf<Pair<Vec, Vec>>()

        val map = input
            .map { line -> line.map { it.digitToInt() } }

        map
            .flatMapIndexed { y, line ->
                line.mapIndexedNotNull { x, i ->
                    if (i == 0) Vec(y, x) else null
                }
            }
            .forEach { z ->
                map.trail(z) { trails.add(Pair(z, it)) }
            }

        return trails.size
    }

    fun part2(input: List<String>): Int {
        val trails = mutableListOf<Pair<Vec, Vec>>()

        val map = input
            .map { line -> line.map { it.digitToInt() } }

        map
            .flatMapIndexed { y, line ->
                line.mapIndexedNotNull { x, i ->
                    if (i == 0) Vec(y, x) else null
                }
            }
            .forEach { z ->
                map.trail(z) { trails.add(Pair(z, it)) }
            }

        return trails.size
    }
}
