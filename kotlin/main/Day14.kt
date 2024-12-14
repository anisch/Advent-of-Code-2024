import Day14.part1
import Day14.part2
import java.io.File

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput, 7, 11) == 12)
    val input = readInput("Day14")
    println(part1(input, 103, 101)) // 208494630 to low

//    check(part2(testInput, 7, 11) == 1)
    println(part2(input, 103, 101))
}

val pRegex = """(p=)\d+,\d+""".toRegex()
val vRegex = """(v=)-?\d+,-?\d+""".toRegex()

private fun String.toVec(): Vec {
    val (x, y) = drop(2).split(",").map { it.toInt() }
    return Vec(y, x)
}

data class Robot(var pos: Vec, val move: Vec)

private fun Robot.move(my: Int, mx: Int) {
    pos = pos.next(move, my, mx)
}

private operator fun Vec.plus(o: Vec) = Vec(y + o.y, x + o.x)

private fun Vec.next(move: Vec, my: Int, mx: Int): Vec {
    var (y, x) = this + move
    if (y !in 0..<my) {
        y = (if (move.y < 0) y + my else y - my)
    }
    if (x !in 0..<mx) {
        x = (if (move.x < 0) x + mx else x - mx)
    }
    return Vec(y, x)
}

fun printRoom(robots: List<Robot>, sizeY: Int, sizeX: Int): String = buildString {
    for (y in 0..<sizeY) {
        for (x in 0..<sizeX) {
            val h = robots.filter { it.pos == Vec(y, x) }
            if (h.isNotEmpty()) append("#")
            else append(".")
        }
        appendLine()
    }
    appendLine()
}

object Day14 {
    fun part1(input: List<String>, sizeY: Int, sizeX: Int): Int {
        val robots = input
            .map { line ->
                val p = pRegex.find(line)
                val v = vRegex.find(line)
                Robot(p!!.value.toVec(), v!!.value.toVec())
            }

        repeat(100) {
            robots.forEach { it.move(sizeY, sizeX) }
        }

        val ym = sizeY ushr 1
        val xm = sizeX ushr 1

        val q1 = robots.filter { r -> r.pos.y in 0..<ym && r.pos.x in 0..<xm }
        val q2 = robots.filter { r -> r.pos.y in ym + 1..sizeY && r.pos.x in 0..<xm }
        val q3 = robots.filter { r -> r.pos.y in 0..<ym && r.pos.x in xm + 1..sizeX }
        val q4 = robots.filter { r -> r.pos.y in ym + 1..sizeY && r.pos.x in xm + 1..sizeX }

        return q1.size * q2.size * q3.size * q4.size
    }

    fun part2(input: List<String>, sizeY: Int, sizeX: Int): Int {
        val robots = input
            .map { line ->
                val p = pRegex.find(line)
                val v = vRegex.find(line)
                Robot(p!!.value.toVec(), v!!.value.toVec())
            }

        val file = File("tree.txt")
        file.delete()

        repeat(10000) {
            robots.forEach { it.move(sizeY, sizeX) }
            file.appendText("+++++ step: ${it + 1} +++++\n")
            file.appendText(printRoom(robots, sizeY, sizeX))
        }

        return input.size
    }
}
