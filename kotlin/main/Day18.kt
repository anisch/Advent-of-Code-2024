import Day18.part1
import Day18.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput, 7, 12) == 22)
    val input = readInput("Day18")
    println(part1(input, 71, 1024))

    check(part2(testInput, 7, 12) == "6,1")
    println(part2(input, 71, 1024))
}

private fun Array<Array<String>>.next(v: Vec): List<Vec> {
    val next = mutableListOf<Vec>()
    // left
    if (v.x > 0 && this[v.y][v.x - 1] != "#") {
        next += Vec(v.y, v.x - 1)
    }
    // up
    if (v.y > 0 && this[v.y - 1][v.x] != "#") {
        next += Vec(v.y - 1, v.x)
    }
    // right
    if (v.x < this[0].lastIndex && this[v.y][v.x + 1] != "#") {
        next += Vec(v.y, v.x + 1)
    }
    //down
    if (v.y < this.lastIndex && this[v.y + 1][v.x] != "#") {
        next += Vec(v.y + 1, v.x)
    }

    return next
}

object Day18 {
    fun part1(input: List<String>, size: Int, first: Int): Int {
        val vecs = input
            .map { line ->
                val (x, y) = line.split(",").map { it.toInt() }
                Vec(y, x)
            }

        val line = Array(size) { "." }
        val map = Array(size) { it -> line.clone() }

        vecs.take(first).forEach { map[it.y][it.x] = "#" }

        val start = Vec(0, 0)
        val end = Vec(size - 1, size - 1)

        val frontier = ArrayDeque<Vec>()
        frontier.add(start)

        val prevNode = mutableMapOf<Vec, Vec?>()
        prevNode[start] = null

        val cost = mutableMapOf<Vec, Int>()
        cost[start] = 0

        while (frontier.isNotEmpty()) {
            val current = frontier.removeFirst()
            for (next in map.next(current)) {
                var nextCost = cost[current]!! + 1
                if (next !in prevNode || nextCost < cost[next]!!) {
                    frontier.add(next)
                    prevNode[next] = current
                    cost[next] = nextCost
                }
            }
        }

        var current = end
        val path = mutableListOf<Vec>()
        while (current != start) {
            path += current
            current = prevNode[current]!!
        }
        return path.size
    }

    fun part2(input: List<String>, size: Int, first: Int): String {
        val vecs = input
            .map { line ->
                val (x, y) = line.split(",").map { it.toInt() }
                Vec(y, x)
            }


        var result = Vec(0, 0)
        loop@ for (x in first..vecs.lastIndex) {
            val line = Array(size) { "." }
            val map = Array(size) { it -> line.clone() }
            vecs.take(x+1).forEach { map[it.y][it.x] = "#" }

            val start = Vec(0, 0)
            val end = Vec(size - 1, size - 1)

            val frontier = ArrayDeque<Vec>()
            frontier.add(start)

            val prevNode = mutableMapOf<Vec, Vec?>()
            prevNode[start] = null

            val cost = mutableMapOf<Vec, Int>()
            cost[start] = 0

            while (frontier.isNotEmpty()) {
                val current = frontier.removeFirst()
                for (next in map.next(current)) {
                    var nextCost = cost[current]!! + 1
                    if (next !in prevNode || nextCost < cost[next]!!) {
                        frontier.add(next)
                        prevNode[next] = current
                        cost[next] = nextCost
                    }
                }
            }

            var current = end
            val path = mutableListOf<Vec>()
            while (current != start) {
                path += current
                val prev = prevNode[current]
                if (prev == null) {
                    result = vecs[x]
                    break@loop
                } else current = prevNode[current]!!
            }
        }
        return "${result.x},${result.y}"
    }
}
