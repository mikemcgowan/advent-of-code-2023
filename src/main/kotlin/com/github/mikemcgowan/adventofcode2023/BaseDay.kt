package com.github.mikemcgowan.adventofcode2023

import org.jline.terminal.Terminal
import org.springframework.core.io.Resource
import java.io.BufferedReader

abstract class BaseDay(terminal: Terminal) {
    private val writer = terminal.writer()

    protected fun run(input: Resource, skipPart1: Boolean, skipPart2: Boolean) {
        val lines = resourceToLines(input)
        if (!skipPart1) writer.println("Part 1: " + part1(lines))
        if (!skipPart2) writer.println("Part 2: " + part2(lines))
        writer.flush()
    }

    abstract fun part1(lines: List<String>): Long
    abstract fun part2(lines: List<String>): Long

    private fun resourceToLines(r: Resource) =
        r.inputStream.bufferedReader().use(BufferedReader::readText).split('\n').filter { it.isNotEmpty() }
}
