import Day09.part1
import Day09.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    val input = readInput("Day09")
    println(part1(input))

    check(part2(testInput) == 2858L)
    println(part2(input))
}

fun String.getFileSystem(): MutableList<Int?> {
    var disk = mutableListOf<Int?>()

    var id = 0
    for ((i, c) in withIndex()) {
        val num = c.digitToInt()
        repeat(num) {
            if (i and 1 == 1) disk.add(null)
            else disk.add(id)
        }
        if (i and 1 == 1) id++
    }

    return disk
}

private fun List<*>.findNulls(l: Int, pos: Int): Int? {
    for (i in 0..pos) {
        when {
            this[i] != null -> continue
            i + l > pos -> return null
            subList(i, i + l).all { it == null } -> return i
        }
    }
    return null
}

private fun List<Int?>.sum(): Long {
    var sum = 0L
    forEachIndexed { i, l ->
        l?.let { sum += i * it }
    }
    return sum
}

object Day09 {
    fun part1(input: List<String>): Long {
        var disk = input[0].getFileSystem()

        do {
            val i = disk.indexOfFirst { it == null }
            val j = disk.indexOfLast { it != null }
            if (i < j) disk.swap(i, j)
        } while (i <= j)

        return disk.sum()
    }

    fun part2(input: List<String>): Long {
        var disk = input[0].getFileSystem()

        val max = disk.maxOf { it ?: 0 }
        for (i in max downTo 0) {
            val si = disk.indexOfFirst { it == i }
            val ei = disk.indexOfLast { it == i }
            val length = ei - si + 1
            disk.findNulls(length, si)?.let {
                var n = it
                for (x in si..ei) disk.swap(x, n++)
            }
        }

        return disk.sum()
    }
}
