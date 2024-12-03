import Day03.part1
import Day03.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day03_test1")
    check(part1(testInput1) == 161)
    val input = readInput("Day03")
    println(part1(input))

    val testInput2 = readInput("Day03_test2")
    check(part2(testInput2) == 48)
    println(part2(input))
}

object Day03 {
    fun part1(input: List<String>): Int {
        val matcher = """(mul\()\d+,\d+(\))""".toRegex()

        return input
            .flatMap { line ->
                matcher.findAll(line).map { it.value }
            }
            .map { it.substring(4, it.lastIndex) }
            .map { it.split(",") }
            .sumOf { (a, b) -> a.toInt() * b.toInt() }
    }

    fun part2(input: List<String>): Int {
        val matcher = """(don't[(][)])|(do[(][)])|mul[(]\d+,\d+[)]""".toRegex()

        var enabled = true
        var sum = 0

        input
            .flatMap { line ->
                matcher.findAll(line).map { it.value }
            }
            .forEach {
                when (it) {
                    "do()" -> enabled = true
                    "don't()" -> enabled = false
                    else -> if (enabled) {
                        val (a, b) = it.substring(4, it.lastIndex).split(",")
                        sum += a.toInt() * b.toInt()
                    }
                }
            }

        return sum
    }
}
