import Day01.part1
import Day01.part2
import kotlin.math.absoluteValue

fun main() {
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    val input = readInput("Day01")
    println(part1(input))

    check(part2(testInput) == 31)
    println(part2(input))
}

private fun List<String>.toIntListPair(): Pair<List<Int>, List<Int>> {
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()

    forEach { line ->
        val split = line.split("\\s+".toRegex())
        left.add(split.first().toInt())
        right.add(split.last().toInt())
    }

    left.sort()
    right.sort()

    return left to right
}

object Day01 {
    fun part1(input: List<String>): Int {
        val (left, right) = input.toIntListPair()

        return left
            .mapIndexed { idx, l -> (l - right[idx]).absoluteValue }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val (left, right) = input.toIntListPair()

        return left
            .sumOf { l -> l * right.count { r -> r == l } }
    }
}
