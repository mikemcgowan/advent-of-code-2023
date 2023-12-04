package com.github.mikemcgowan.adventofcode2023.days.day04

import com.github.mikemcgowan.adventofcode2023.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import kotlin.math.pow

@ShellComponent
class Day04(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day04/input.txt")
    lateinit var input: Resource

    @ShellMethod("Day 4")
    fun day4(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        run(input, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long =
        lines.sumOf { score(it) }.toLong()

    override fun part2(lines: List<String>): Long {
        val m = mutableMapOf<Int, Int>()
        (1..lines.size).map { m.put(it, 1) }
        m.map { entry ->
            val n = winning(lines[entry.key - 1])
            val spawn = (entry.key + 1)..(entry.key + n)
            spawn.forEach { m[it] = m[it]!! + entry.value }
        }
        return m.values.sum().toLong()
    }

    private fun winning(line: String): Int {
        val (winningNumbers, ownNumbers) = parse(line)
        return ownNumbers.filter { winningNumbers.contains(it) }.size
    }

    private fun score(line: String): Int {
        val n = winning(line)
        return if (n == 0) 0 else 2.0.pow((n - 1).toDouble()).toInt()
    }

    private fun parse(line: String): Pair<List<Int>, List<Int>> {
        val (s1, s2) = line.split(':')[1].split('|')
        val f: (String) -> List<Int> = { s -> s.split(' ').filter { it.isNotBlank() }.map { it.trim().toInt() } }
        val winningNumbers = f(s1)
        val ownNumbers = f(s2)
        return winningNumbers to ownNumbers
    }
}
