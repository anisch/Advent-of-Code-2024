import Day08.part1
import Day08.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    val input = readInput("Day08")
    println(part1(input))

    check(part2(testInput) == 34)
    println(part2(input))
}

private fun List<String>.findAntennas(): Map<Char, List<Vec>> {
    val result = mutableMapOf<Char, MutableList<Vec>>()

    forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c.isLetterOrDigit()) {
                if (result[c].isNullOrEmpty())
                    result[c] = mutableListOf(Vec(y, x))
                else
                    result[c]?.add(Vec(y, x))
            }
        }
    }

    return result
}

private operator fun Vec.minus(o: Vec): Vec = Vec(y - o.y, x - o.x)
private operator fun Vec.plus(o: Vec): Vec = Vec(y + o.y, x + o.x)
private fun Vec.inRange(map: List<String>): Boolean = y in 0..map.lastIndex && x in 0..map[0].lastIndex

object Day08 {
    fun part1(input: List<String>): Int {
        val antiNodes = mutableSetOf<Vec>()

        input
            .findAntennas()
            .forEach { c, vl ->
                for (i in 0..vl.lastIndex - 1) {
                    for (j in i + 1..vl.lastIndex) {
                        val d = vl[i] - vl[j]
                        antiNodes += vl[i] + Vec(d.y, d.x)
                        antiNodes += vl[j] - Vec(d.y, d.x)
                    }
                }
            }

        return input
            .mapIndexed { y, line ->
                line.mapIndexed { x, ch ->
                    if (Vec(y, x) in antiNodes) 1 else 0
                }.sum()
            }.sum()
    }

    fun part2(input: List<String>): Int {
        val antiNodes = mutableSetOf<Vec>()

        input
            .findAntennas()
            .forEach { c, vl ->
                for (i in 0..vl.lastIndex - 1) {
                    for (j in i + 1..vl.lastIndex) {
                        val d = vl[i] - vl[j]

                        var h = vl[i] + Vec(d.y, d.x)
                        while (h.inRange(input)) {
                            antiNodes += h
                            h = h + Vec(d.y, d.x)
                        }

                        h = vl[j] - Vec(d.y, d.x)
                        while (h.inRange(input)) {
                            antiNodes += h
                            h = h - Vec(d.y, d.x)
                        }
                    }
                }
            }

        return input
            .mapIndexed { y, line ->
                line.mapIndexed { x, ch ->
                    if (Vec(y, x) in antiNodes || ch.isLetterOrDigit()) 1
                    else 0
                }.sum()
            }.sum()
    }
}
