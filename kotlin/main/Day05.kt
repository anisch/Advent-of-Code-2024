import Day05.part1
import Day05.part2

fun MutableList<Int>.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    val input = readInput("Day05")
    println(part1(input))

    check(part2(testInput) == 123)
    println(part2(input))
}

private fun List<Int>.isOrdered(rules: Map<Int, List<Int>>): Boolean {
    for (idx in 0..lastIndex - 1) {
        val page = this[idx]
        rules[page]?.let { ints ->
            if (this[idx + 1] !in ints) return false
        }
    }
    for (idx in lastIndex downTo 1) {
        val page = this[idx]
        rules[page]?.let { ints ->
            if (this[idx - 1] in ints) return false
        }
    }
    return true
}

private fun List<Int>.createFix(rules: Map<Int, List<Int>>): List<Int> {
    val tmp = toMutableList()
    do {
        for (idx in 0..tmp.lastIndex - 1) {
            val page = tmp[idx]
            rules[page]?.let { ints ->
                if (tmp[idx + 1] !in ints) tmp.swap(idx, idx + 1)
            }
            if (tmp.isOrdered(rules)) return tmp.toList()
        }
        for (idx in tmp.lastIndex downTo 1) {
            val page = tmp[idx]
            rules[page]?.let { ints ->
                if (tmp[idx - 1] in ints) tmp.swap(idx, idx - 1)
            }
            if (tmp.isOrdered(rules)) return tmp.toList()
        }
    } while (!tmp.isOrdered(rules))

    return tmp.toList()
}

object Day05 {
    fun part1(input: List<String>): Int {
        val rules = input
            .filter { it.contains("|") }
            .map { it.split("|").map { it.toInt() } }
            .groupBy { it.first() }
            .mapValues { it.value.map { it[1] } }

        val updates = input
            .filter { it.contains(",") }
            .map { it.split(",").map { it.toInt() } }

        val result = updates
            .filter { it.isOrdered(rules) }
            .sumOf { it[(it.lastIndex ushr 1)] }

        return result
    }

    fun part2(input: List<String>): Int {
        val rules = input
            .filter { it.contains("|") }
            .map { it.split("|").map { it.toInt() } }
            .groupBy { it.first() }
            .mapValues { it.value.map { it[1] } }

        val updates = input
            .filter { it.contains(",") }
            .map { it.split(",").map { it.toInt() } }

        return updates
            .filterNot { it.isOrdered(rules) }
            .map { it.createFix(rules) }
            .sumOf { it[(it.lastIndex ushr 1)] }
    }
}
