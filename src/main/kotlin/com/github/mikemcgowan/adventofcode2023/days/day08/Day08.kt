package com.github.mikemcgowan.adventofcode2023.days.day08

import com.github.mikemcgowan.adventofcode2023.days.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

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
        var key = "AAA"
        var steps = 0L
        do {
            key = nextKey(leftRight, steps, map, key)
            ++steps
        } while (key != "ZZZ")
        return steps
    }

    override fun part2(lines: List<String>): Long {
        val (leftRight, map) = parse(lines)
        var keys = map.keys.filter { it.endsWith('A') }
        var steps = 0L
        do {
            keys = keys.map { nextKey(leftRight, steps, map, it) }
            ++steps
        } while (keys.any { !it.endsWith('Z') })
        return steps
    }

    private fun nextKey(leftRight: String, steps: Long, map: Map<String, Pair<String, String>>, key: String): String {
        val i = (steps % leftRight.length).toInt()
        val nextLeftRight = leftRight[i]
        val nextKey = if (nextLeftRight == 'L') map[key]?.first else map[key]?.second
        return nextKey!!
    }

    private fun parse(lines: List<String>): Pair<String, Map<String, Pair<String, String>>> {
        val leftRight = lines[0]
        val map = lines.drop(1).associate { line ->
            val ys = line.split('=')
            val zs = ys[1].split(',')
            ys[0].trim() to (zs[0].trim().drop(1) to zs[1].trim().dropLast(1))
        }
        return leftRight to map
    }
}
