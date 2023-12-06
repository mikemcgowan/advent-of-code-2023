package com.github.mikemcgowan.adventofcode2023.days.day05

import com.github.mikemcgowan.adventofcode2023.days.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import kotlin.math.max
import kotlin.math.min

@ShellComponent
class Day05(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day05/input.txt")
    lateinit var input: Resource

    data class Line(private val destinationRangeStart: Long, private val sourceRangeStart: Long, private val rangeLength: Long) {
        val sourceRange = sourceRangeStart until (sourceRangeStart + rangeLength)
        var destinationRange = destinationRangeStart until (destinationRangeStart + rangeLength)

        fun mapRange(r: LongRange): Triple<LongRange?, LongRange?, LongRange?> =
            when {
                r.first > sourceRange.last -> Triple(null, null, r)
                r.last < sourceRange.first -> Triple(null, r, null)
                else -> {
                    val toBeMapped = max(r.first, sourceRange.first)..min(r.last, sourceRange.last)
                    val offset = toBeMapped.first - sourceRange.first
                    val mapped = (destinationRange.first + offset)..(destinationRange.first + offset + toBeMapped.last - toBeMapped.first)
                    val leftOverBelow = if (r.first < sourceRange.first) r.first until sourceRange.first else null
                    val leftOverAbove = if (r.last > sourceRange.last) (sourceRange.last + 1)..r.last else null
                    Triple(mapped, leftOverBelow, leftOverAbove)
                }
            }
    }

    data class Lines(val lines: List<Line>) {
        fun map(source: Long): Long {
            val line = lines.find { source in it.sourceRange } ?: return source
            val offset = source - line.sourceRange.first
            return line.destinationRange.first + offset
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
        val rangesToBeMapped = pairs.map { it.first until (it.first + it.second) }.toMutableSet()
        val mappedRanges = mutableListOf<LongRange>()
        xs.forEach { ys ->
            ys.lines.forEach { line ->
                rangesToBeMapped.toList().forEach { range ->
                    rangesToBeMapped.remove(range)
                    val (mapped, below, above) = line.mapRange(range)
                    mapped?.let { mappedRanges.add(it) }
                    below?.let { rangesToBeMapped.add(it) }
                    above?.let { rangesToBeMapped.add(it) }
                }
            }
            rangesToBeMapped.addAll(mappedRanges)
            mappedRanges.clear()
        }
        return rangesToBeMapped.minOf { it.first }
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
