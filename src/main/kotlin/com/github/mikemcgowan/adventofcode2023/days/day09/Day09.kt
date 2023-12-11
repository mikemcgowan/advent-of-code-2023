package com.github.mikemcgowan.adventofcode2023.days.day09

import com.github.mikemcgowan.adventofcode2023.days.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class Day09(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day09/input.txt")
    lateinit var input: Resource

    @ShellMethod("Day 9")
    fun day9(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        run(input, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long =
        lines.sumOf { line ->
            val xs = parseLine(line)
            expandLine(listOf(xs)).sumOf { it.last() }
        }

    override fun part2(lines: List<String>): Long =
        lines.sumOf { line ->
            val xs = parseLine(line)
            expandLine(listOf(xs)).reversed().fold(0L) { acc, ys -> ys.first() - acc }
        }

    private tailrec fun expandLine(xss: List<List<Long>>): List<List<Long>> {
        val xs = xss.last()
        if (xs.all { it == 0L }) return xss
        val ys = xs.zip(xs.drop(1)).map { it.second - it.first }
        return expandLine(xss.plusElement(ys))
    }

    private fun parseLine(s: String): List<Long> =
        s.split(' ').map { it.toLong() }
}
