package com.github.mikemcgowan.adventofcode2023.days.day03

import com.github.mikemcgowan.adventofcode2023.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

typealias Point = Pair<Int, Int>
typealias Cell = Pair<Char, Point>

@ShellComponent
class Day03(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day03/input.txt")
    lateinit var input: Resource

    private var gearMap: MutableMap<Cell, List<Long>>? = null

    @ShellMethod("Day 3")
    fun day3(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        run(input, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long =
        go(lines).sum()

    override fun part2(lines: List<String>): Long {
        gearMap = mutableMapOf()
        go(lines)
        return gearMap?.filter { it.value.size == 2 }?.map { it.value[0] * it.value[1] }?.sum() ?: -1
    }

    private fun go(lines: List<String>): List<Long> {
        val partNumbers = mutableListOf<Long>()
        val cur = mutableListOf<Cell>()
        var row = 0
        var col: Int
        lines.forEach { line ->
            col = 0
            cur.clear()
            line.forEach { x ->
                when {
                    x.isDigit() -> cur.add(x to (row to col))
                    else -> maybeAddPart(cur, partNumbers, lines)
                }
                ++col
            }
            maybeAddPart(cur, partNumbers, lines)
            ++row
        }
        return partNumbers
    }

    private fun maybeAddPart(part: MutableList<Cell>, partNumbers: MutableList<Long>, lines: List<String>) {
        if (part.isEmpty()) return
        val adjacentSymbols = part.map { it.second }.map { adjacentSymbols(it, lines) }
        val addPart = adjacentSymbols.any { it.isNotEmpty() }
        if (addPart) {
            val n = part.joinToString("") { it.first.toString() }.toLong()
            partNumbers.add(n)
            if (gearMap != null) maybeAddToGearMap(adjacentSymbols, n)
        }
        part.clear()
    }

    private fun maybeAddToGearMap(adjacentSymbols: List<List<Cell>>, n: Long) {
        adjacentSymbols.flatten().filter { it.first == '*' }.distinctBy { it.second }.forEach { gear ->
            gearMap?.compute(gear) { _, v -> v?.plus(n) ?: listOf(n) }
        }
    }

    private fun adjacentSymbols(p: Point, lines: List<String>): List<Cell> {
        val checkForSymbolsAt = listOf(
            p.first - 1 to p.second - 1,
            p.first - 1 to p.second + 0,
            p.first - 1 to p.second + 1,
            p.first + 0 to p.second - 1,
            p.first + 0 to p.second + 1,
            p.first + 1 to p.second - 1,
            p.first + 1 to p.second + 0,
            p.first + 1 to p.second + 1,
        ).filter { it.first >= 0 && it.second >= 0 && it.first < lines.size && it.second < lines[0].length }
        val xs = mutableListOf<Cell>()
        checkForSymbolsAt.forEach {
            val x = lines[it.first][it.second]
            if (!(x.isDigit() || x == '.')) xs.add(x to it)
        }
        return xs
    }
}
