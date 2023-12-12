package com.github.mikemcgowan.adventofcode2023.days.day10

import com.github.mikemcgowan.adventofcode2023.days.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

typealias Grid = Array<CharArray>

@ShellComponent
class Day10(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day10/input.txt")
    lateinit var input: Resource

    enum class Dir { NORTH, EAST, SOUTH, WEST }
    data class Point(val x: Int, val y: Int)

    @ShellMethod("Day 10")
    fun day10(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        run(input, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long {
        val grid = parse(lines)
        val s = findS(grid)
        return run(grid, s).toLong()
    }

    override fun part2(lines: List<String>): Long = 0

    private fun run(grid: Grid, s: Point): Int {
        var currentPoint = s
        var direction = possibleDirections(currentPoint, grid).first()
        var steps = 0
        do {
            currentPoint = move(currentPoint, direction)
            direction = changeDirection(grid[currentPoint.y][currentPoint.x], direction)
            ++steps
        } while (currentPoint != s)
        return steps / 2
    }

    private fun findS(grid: Grid): Point {
        val sy = grid.indexOfFirst { cs -> cs.contains('S') }
        val sx = grid[sy].indexOfFirst { it == 'S' }
        return Point(sx, sy)
    }

    private fun possibleDirections(point: Point, grid: Grid): List<Dir> {
        val gridWidth = grid[0].size
        val gridHeight = grid.size
        val dirs = mutableListOf<Dir>()
        if (point.x < (gridWidth - 1) && grid[point.y][point.x + 1] in listOf('-', 'J', '7')) dirs.add(Dir.EAST)
        if (point.x > 0 && grid[point.y][point.x - 1] in listOf('-', 'F', 'L')) dirs.add(Dir.WEST)
        if (point.y < (gridHeight - 1) && grid[point.y + 1][point.x] in listOf('|', 'L', 'J')) dirs.add(Dir.SOUTH)
        if (point.y > 0 && grid[point.y - 1][point.x] in listOf('|', 'F', '7')) dirs.add(Dir.NORTH)
        return dirs
    }

    private fun move(point: Point, dir: Dir): Point =
        when (dir) {
            Dir.NORTH -> Point(point.x, point.y - 1)
            Dir.EAST -> Point(point.x + 1, point.y)
            Dir.SOUTH -> Point(point.x, point.y + 1)
            Dir.WEST -> Point(point.x - 1, point.y)
        }

    private fun changeDirection(pipe: Char, dir: Dir): Dir =
        when (pipe) {
            'L' -> if (dir == Dir.SOUTH) Dir.EAST else Dir.NORTH
            'J' -> if (dir == Dir.SOUTH) Dir.WEST else Dir.NORTH
            '7' -> if (dir == Dir.NORTH) Dir.WEST else Dir.SOUTH
            'F' -> if (dir == Dir.NORTH) Dir.EAST else Dir.SOUTH
            else -> dir
        }

    private fun parse(lines: List<String>): Grid =
        lines.map { it.toCharArray() }.toTypedArray()
}
