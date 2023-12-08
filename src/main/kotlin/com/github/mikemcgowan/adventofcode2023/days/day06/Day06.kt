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

    data class Race(val time: Int, val distance: Int) {
        fun waysToBeatRecord(): Int {
            val ways = (1..time / 2)
                .map { holdFor -> holdFor * (time - holdFor) }
                .filter { it > distance }.size * 2
            return if (ways > 0 && time % 2 == 0) ways - 1 else ways
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
        parse(lines).map { it.waysToBeatRecord() }.fold(1) { acc, n -> acc * n }

    override fun part2(lines: List<String>): Long = 0

    private fun parse(lines: List<String>): List<Race> {
        val f: (String) -> List<Int> =
            { s -> s.split(':')[1].split(Regex("""\s+""")).filter { it.isNotBlank() }.map { it.trim().toInt() } }
        val times = f(lines[0])
        val records = f(lines[1])
        return times.zip(records).map { Race(it.first, it.second) }
    }
}
