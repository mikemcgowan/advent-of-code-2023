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
    enum class Cell(val c: Char) { OUTSIDE('O'), PIPE('X'), INSIDE('I') }
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
        val steps = getSteps(grid, s)
        return steps.size / 2L
    }

    override fun part2(lines: List<String>): Long {
        val grid = parse(lines)
        val s = findS(grid)
        val pipe = getSteps(grid, s)
        val zs = grid.mapIndexed { y, xs ->
            val init = Cell.OUTSIDE to listOf<Cell>()
            xs.foldIndexed(init) { x, (state, cells), c ->
                assert(c == grid[y][x])
                val inPipe = Point(x, y) in pipe
                when {
                    inPipe && c == '|' -> flipState(state) to cells.plus(Cell.PIPE)
                    inPipe && c == '7' -> maybeFlipState('L', state, xs.take(x)) to cells.plus(Cell.PIPE)
                    inPipe && c == 'J' -> maybeFlipState('F', state, xs.take(x)) to cells.plus(Cell.PIPE)
                    inPipe -> state to cells.plus(Cell.PIPE)
                    else -> state to cells.plus(state)
                }
            }.second
        }
        return zs.sumOf { xs -> xs.count { it == Cell.INSIDE } }.toLong()
    }

    private fun maybeFlipState(flipIfEnteredAt: Char, state: Cell, cs: List<Char>): Cell {
        val enteredAt = cs.dropLastWhile { it == '-' }.last()
        return if (enteredAt == flipIfEnteredAt) flipState(state) else state
    }

    private fun flipState(c: Cell): Cell =
        when (c) {
            Cell.INSIDE -> Cell.OUTSIDE
            Cell.OUTSIDE -> Cell.INSIDE
            else -> c
        }

    private fun getSteps(grid: Grid, s: Point): Set<Point> {
        var currentPoint = s
        var direction = possibleDirections(currentPoint, grid).first()
        val steps = mutableSetOf<Point>()
        do {
            currentPoint = move(currentPoint, direction)
            direction = changeDirection(grid[currentPoint.y][currentPoint.x], direction)
            steps.add(currentPoint)
        } while (currentPoint != s)
        return steps
    }

    private fun findS(grid: Grid): Point {
        val sy = grid.indexOfFirst { it.contains('S') }
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
