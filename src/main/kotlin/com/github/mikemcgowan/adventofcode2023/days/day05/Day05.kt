package com.github.mikemcgowan.adventofcode2023.days.day05

import com.github.mikemcgowan.adventofcode2023.days.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class Day05(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day05/input.txt")
    lateinit var input: Resource

    data class Line(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long)
    data class Lines(val lines: List<Line>) {
        fun map(source: Long): Long {
            val line = lines.find { line ->
                source >= line.sourceRangeStart && source <= line.sourceRangeStart + line.rangeLength
            } ?: return source
            val offset = source - line.sourceRangeStart
            return line.destinationRangeStart + offset
        }
    }

    @ShellMethod("Day 5")
    fun day5(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        run(input, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long {
        val (seeds, xs) = parse(lines)
        val results = seeds.map { seed ->
            xs.fold(seed) { acc, ys -> ys.map(acc) }
        }
        return results.min().toLong()
    }

    override fun part2(lines: List<String>): Long {
        val (part1Seeds, xs) = parse(lines)
        val pairs = (0 until part1Seeds.size / 2).map { part1Seeds[2 * it] to part1Seeds[2 * it + 1] }
        val seeds = pairs.map { it.first until (it.first + it.second) }.flatten()
        var min = -1L
        seeds.forEach { seed ->
            val result = xs.fold(seed) { acc, ys -> ys.map(acc) }
            if (min == -1L || result < min) min = result
        }
        return min
    }

    private fun parse(xs: List<String>): Pair<List<Long>, List<Lines>> {
        val seeds = xs[0].split(':')[1].split(' ').filter { it.isNotBlank() }.map { it.trim().toLong() }
        val lines = mutableListOf<Lines>()
        val cur = mutableListOf<Line>()
        xs.drop(2).filter { it.isNotBlank() }.forEach { line ->
            if (line.endsWith("map:")) {
                if (cur.isNotEmpty()) {
                    lines.add(Lines(cur.toList()))
                    cur.clear()
                }
            } else {
                val ys = line.split(' ').map { it.toLong() }
                cur.add(Line(ys[0], ys[1], ys[2]))
            }
        }
        if (cur.isNotEmpty()) {
            lines.add(Lines(cur.toList()))
        }
        return seeds to lines
    }
}
