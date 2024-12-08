import Day04.part1
import Day04.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    val input = readInput("Day04")
    println(part1(input))

    check(part2(testInput) == 9)
    println(part2(input))
}

private fun List<String>.isXmas(x: Vec, m: Vec, a: Vec, s: Vec): Boolean {
    return this[x.y][x.x] == 'X' && this[m.y][m.x] == 'M' && this[a.y][a.x] == 'A' && this[s.y][s.x] == 'S'
}

private fun List<String>.isXMas(a: Vec): Boolean = when {
    this[a.y][a.x] != 'A' -> false
    this[a.y - 1][a.x - 1] !in listOf('M', 'S') -> false
    this[a.y + 1][a.x - 1] !in listOf('M', 'S') -> false
    this[a.y - 1][a.x + 1] !in listOf('M', 'S') -> false
    this[a.y + 1][a.x + 1] !in listOf('M', 'S') -> false
    this[a.y - 1][a.x - 1] == this[a.y + 1][a.x + 1] -> false
    this[a.y + 1][a.x - 1] == this[a.y - 1][a.x + 1] -> false
    else -> true
}

private fun List<String>.countXMAS(): Int {
    var count = (0..lastIndex).sumOf { y ->
        (0..this[y].lastIndex - 3).count { x ->
            isXmas(Vec(y, x), Vec(y, x + 1), Vec(y, x + 2), Vec(y, x + 3)) ||
                    isXmas(Vec(y, x + 3), Vec(y, x + 2), Vec(y, x + 1), Vec(y, x))
        }
    }
    count += (0..this[0].lastIndex).sumOf { x ->
        (0..this.lastIndex - 3).count { y ->
            isXmas(Vec(y, x), Vec(y + 1, x), Vec(y + 2, x), Vec(y + 3, x)) ||
                    isXmas(Vec(y + 3, x), Vec(y + 2, x), Vec(y + 1, x), Vec(y, x))
        }
    }
    count += (0..lastIndex - 3).sumOf { y ->
        (0..this[y].lastIndex - 3).count { x ->
            isXmas(Vec(y, x), Vec(y + 1, x + 1), Vec(y + 2, x + 2), Vec(y + 3, x + 3)) ||
                    isXmas(Vec(y + 3, x + 3), Vec(y + 2, x + 2), Vec(y + 1, x + 1), Vec(y, x))
        }
    }
    count += (0..lastIndex - 3).sumOf { y ->
        (3..this[y].lastIndex).count { x ->
            isXmas(Vec(y, x), Vec(y + 1, x - 1), Vec(y + 2, x - 2), Vec(y + 3, x - 3)) ||
                    isXmas(Vec(y + 3, x - 3), Vec(y + 2, x - 2), Vec(y + 1, x - 1), Vec(y, x))
        }
    }
    return count
}

private fun List<String>.countCrossMAS() = (1..lastIndex - 1).sumOf { y ->
    (1..this[y].lastIndex - 1).count { x -> isXMas(Vec(y, x)) }
}

object Day04 {
    fun part1(input: List<String>): Int {
        return input.countXMAS()
    }

    fun part2(input: List<String>): Int {
        return input.countCrossMAS()
    }
}
