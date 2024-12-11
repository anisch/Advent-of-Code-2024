import Day11.part1
import Day11.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput, 25) == 55312L)
    val input = readInput("Day11")
    println(part1(input, 25))

    check(part2(testInput, 25) == 55312L)
    println(part2(input, 75))
}

fun List<String>.getStones() = first()
    .split(" ")
    .map { it.toLong() }
    .groupBy { it }
    .mapValues { it.value.size.toLong() }

fun Map<Long, Long>.transform(): Map<Long, Long> {
    var tmp = mutableMapOf<Long, Long>()
    for ((k, v) in this) {
        when {
            k == 0L -> {
                val t = tmp[1]
                tmp[1] = v + (t ?: 0)
            }

            (k.toString().length) and 1 == 0 -> {
                val s = k.toString()
                val m = s.length ushr 1
                val a = s.substring(0..<m).toLong()
                val b = s.substring(m..s.lastIndex).toLong()

                val ta = tmp[a]
                tmp[a] = v + (ta ?: 0)

                val tb = tmp[b]
                tmp[b] = v + (tb ?: 0)
            }

            else -> {
                val h = k * 2024L
                val t = tmp[h]
                tmp[h] = v + (t ?: 0)
            }
        }
    }
    return tmp
}

object Day11 {
    fun part1(input: List<String>, repeat: Int): Long {
        var stones = input.getStones()

        repeat(repeat) {
            stones = stones.transform()
        }

        return stones.values.sum()
    }

    fun part2(input: List<String>, repeat: Int): Long {
        var stones = input.getStones()

        repeat(repeat) {
            stones = stones.transform()
        }

        return stones.values.sum()
    }
}
