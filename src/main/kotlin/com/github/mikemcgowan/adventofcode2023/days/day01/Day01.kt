package com.github.mikemcgowan.adventofcode2023.days.day01

import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import java.io.BufferedReader

@ShellComponent
class Day01(terminal: Terminal) {
    private val writer = terminal.writer()
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

    @Value("classpath:days/day01/input.txt")
    lateinit var input: Resource

    @ShellMethod("Day 1")
    fun day1(@ShellOption(defaultValue = "false") skipPart1: Boolean, @ShellOption(defaultValue = "false") skipPart2: Boolean) {
        val lines = input.inputStream.bufferedReader().use(BufferedReader::readText).split('\n').filter { it.isNotEmpty() }
        if (!skipPart1) writer.println("Part1: " + part1(lines))
        if (!skipPart2) writer.println("Part2: " + part2(lines))
        writer.flush()
    }

    fun part1(lines: List<String>): Int = lines
        .map { it.first { x -> x.isDigit() } to it.last { x -> x.isDigit() } }
        .sumOf { "${it.first}${it.second}".toInt() }

    fun part2(lines: List<String>): Int =
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
