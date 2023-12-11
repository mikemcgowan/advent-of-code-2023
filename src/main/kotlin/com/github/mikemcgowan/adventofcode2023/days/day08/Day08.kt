package com.github.mikemcgowan.adventofcode2023.days.day08

import com.github.mikemcgowan.adventofcode2023.days.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import java.util.function.Predicate

typealias Lookup = Map<String, Pair<String, String>>

@ShellComponent
class Day08(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day08/input.txt")
    lateinit var input: Resource

    @ShellMethod("Day 8")
    fun day8(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        run(input, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long {
        val (leftRight, map) = parse(lines)
        return stepsFromTo(leftRight, map, "AAA") { it == "ZZZ" }
    }

    override fun part2(lines: List<String>): Long {
        val (leftRight, map) = parse(lines)
        val keysEndingA = map.keys.filter { it.endsWith('A') }
        val stepsToZ = keysEndingA.map { start -> stepsFromTo(leftRight, map, start) { it.endsWith('Z') } }
        return stepsToZ.drop(1).fold(stepsToZ[0]) { acc, n -> findLCM(acc, n) }
    }

    private fun stepsFromTo(leftRight: String, map: Lookup, from: String, terminate: Predicate<String>): Long {
        var key = from
        var steps = 0L
        do {
            key = nextKey(steps, leftRight, map, key)
            ++steps
        } while (!terminate.test(key))
        return steps
    }

    private fun nextKey(steps: Long, leftRight: String, map: Lookup, key: String): String {
        val i = (steps % leftRight.length).toInt()
        val nextLeftRight = leftRight[i]
        val nextKey = if (nextLeftRight == 'L') map[key]?.first else map[key]?.second
        return nextKey!!
    }

    // https://www.baeldung.com/kotlin/lcm
    private fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    private fun parse(lines: List<String>): Pair<String, Lookup> {
        val leftRight = lines[0]
        val map = lines.drop(1).associate { line ->
            val ys = line.split('=')
            val zs = ys[1].split(',')
            ys[0].trim() to (zs[0].trim().drop(1) to zs[1].trim().dropLast(1))
        }
        return leftRight to map
    }
}
