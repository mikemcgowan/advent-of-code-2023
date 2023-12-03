package com.github.mikemcgowan.adventofcode2023

import org.jline.terminal.Terminal
import org.springframework.core.io.Resource
import java.io.BufferedReader

abstract class BaseDay(terminal: Terminal) {
    private val writer = terminal.writer()

    fun run(lines: List<String>, skipPart1: Boolean, skipPart2: Boolean) {
        if (!skipPart1) writer.println("Part1: " + part1(lines))
        if (!skipPart2) writer.println("Part2: " + part2(lines))
        writer.flush()
    }

    abstract fun part1(lines: List<String>): Long
    abstract fun part2(lines: List<String>): Long

    protected fun resourceToLines(r: Resource) =
        r.inputStream.bufferedReader().use(BufferedReader::readText).split('\n').filter { it.isNotEmpty() }
}