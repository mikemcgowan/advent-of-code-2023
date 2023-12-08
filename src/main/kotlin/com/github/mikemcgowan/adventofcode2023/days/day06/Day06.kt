package com.github.mikemcgowan.adventofcode2023.days.day06

import com.github.mikemcgowan.adventofcode2023.days.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class Day06(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day06/input.txt")
    lateinit var input: Resource

    var optimized = true

    data class Race(val time: Long, val distance: Long) {
        fun waysToBeatRecord(optimized: Boolean): Int {
            val ways = if (optimized) waysToBeatRecordFast() else waysToBeatRecordSlow()
            return if (ways > 0 && time % 2L == 0L) ways - 1 else ways
        }

        private fun waysToBeatRecordSlow(): Int =
            (1..time / 2)
                .map { holdFor -> holdFor * (time - holdFor) }
                .filter { it > distance }.size * 2

        private fun waysToBeatRecordFast(): Int {
            val xs = 1..time / 2
            val ys = xs.takeWhile { holdFor -> holdFor * (time - holdFor) <= distance }
            return 2 * ((xs.last - xs.first + 1).toInt() - ys.size)
        }
    }

    @ShellMethod("Day 6")
    fun day6(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        run(input, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long =
        parsePart1(lines).map { it.waysToBeatRecord(optimized) }.fold(1) { acc, n -> acc * n }

    override fun part2(lines: List<String>): Long =
        parsePart2(lines).waysToBeatRecord(optimized).toLong()

    private fun parsePart1(lines: List<String>): List<Race> {
        val f: (String) -> List<Long> =
            { s -> s.split(':')[1].split(Regex("""\s+""")).filter { it.isNotBlank() }.map { it.trim().toLong() } }
        val times = f(lines[0])
        val records = f(lines[1])
        return times.zip(records).map { Race(it.first, it.second) }
    }

    private fun parsePart2(lines: List<String>): Race {
        val f: (String) -> Long =
            { s -> s.split(':')[1].filter { !it.isWhitespace() }.toLong() }
        val time = f(lines[0])
        val record = f(lines[1])
        return Race(time, record)
    }
}
