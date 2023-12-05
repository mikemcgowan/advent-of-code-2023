package com.github.mikemcgowan.adventofcode2023.days.day01

import com.github.mikemcgowan.adventofcode2023.days.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class Day01(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day01/input.txt")
    lateinit var input: Resource

    private val wordToDigit = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
    )

    @ShellMethod("Day 1")
    fun day1(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        run(input, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long = lines
        .map { it.first { x -> x.isDigit() } to it.last { x -> x.isDigit() } }
        .sumOf { "${it.first}${it.second}".toInt() }
        .toLong()

    override fun part2(lines: List<String>): Long =
        part1(lines.map { line ->
            val a = find(line, line.indices)
            val b = find(line, line.indices.reversed())
            "$a$b"
        })

    private fun find(line: String, indices: IntProgression): String? {
        indices.forEach { index ->
            if (line[index].isDigit()) return line[index].toString()
            wordToDigit.entries.forEach { entry ->
                if (line.drop(index).startsWith(entry.key)) return entry.value
            }
        }
        return null
    }
}
