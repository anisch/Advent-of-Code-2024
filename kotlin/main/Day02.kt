import Day02.part1
import Day02.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    val input = readInput("Day02")
    println(part1(input))

    check(part2(testInput) == 4)
    println(part2(input))
}

private fun List<Int>.isDecSorted(): Boolean {
    var dec = true
    for (i in 0..<lastIndex) {
        val diff = (this[i] - this[i + 1])
        if (diff !in 1..3) {
            dec = false
            break
        }
    }
    return dec
}

private fun List<Int>.isIncSorted(): Boolean {
    var inc = true
    for (i in 0..<lastIndex) {
        val diff = (this[i + 1] - this[i])
        if (diff !in 1..3) {
            inc = false
            break
        }
    }
    return inc
}

private fun List<Int>.isDecSortedFixed(): Boolean {
    if (isDecSorted()) return true

    var dec = false
    for (i in indices) {
        val tmpList = toMutableList()
        tmpList.removeAt(i)
        if (tmpList.isDecSorted()) {
            dec = true
            break
        }
    }

    return dec
}

private fun List<Int>.isIncSortedFixed(): Boolean {
    if (isIncSorted()) return true

    var inc = false
    for (i in indices) {
        val tmpList = toMutableList()
        tmpList.removeAt(i)
        if (tmpList.isIncSorted()) {
            inc = true
            break
        }
    }

    return inc
}

object Day02 {
    fun part1(input: List<String>): Int {
        return input
            .map { line ->
                line
                    .split("\\s+".toRegex())
                    .map { s -> s.toInt() }
            }
            .count {
                if (it.first() < it.last()) it.isIncSorted()
                else it.isDecSorted()
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { line ->
                line
                    .split("\\s+".toRegex())
                    .map { s -> s.toInt() }
            }
            .count {
                if (it.first() < it.last()) it.isIncSortedFixed()
                else it.isDecSortedFixed()
            }
    }
}
