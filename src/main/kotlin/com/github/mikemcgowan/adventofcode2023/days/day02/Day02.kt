package com.github.mikemcgowan.adventofcode2023.days.day02

import com.github.mikemcgowan.adventofcode2023.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class Day02(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day02/input.txt")
    lateinit var input: Resource

    private val maxRed = 12
    private val maxGreen = 13
    private val maxBlue = 14

    data class Reveal(val red: Int, val green: Int, val blue: Int)
    data class Game(val id: Int, val reveals: List<Reveal>)

    @ShellMethod("Day 2")
    fun day2(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        val lines = resourceToLines(input)
        run(lines, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long =
        parse(lines).filter { gamePossible(it) }.sumOf { it.id }.toLong()

    override fun part2(lines: List<String>): Long =
        parse(lines).map { minSetOfCubes(it) }.sumOf { it.red * it.green * it.blue }.toLong()

    private fun gamePossible(game: Game): Boolean =
        game.reveals.all { reveal -> reveal.red <= maxRed && reveal.blue <= maxBlue && reveal.green <= maxGreen }

    private fun minSetOfCubes(game: Game): Reveal {
        var red = 0
        var green = 0
        var blue = 0
        game.reveals.forEach { reveal ->
            if (reveal.red > red) red = reveal.red
            if (reveal.green > green) green = reveal.green
            if (reveal.blue > blue) blue = reveal.blue
        }
        return Reveal(red, green, blue)
    }

    private fun parse(lines: List<String>): List<Game> = lines.map { line ->
        val xs = line.split(':')
        val id = xs[0].drop("Game ".length).toInt()
        val ys = xs[1].split(';')
        val reveals = ys.map { reveal ->
            val zs = reveal.split(',')
            var red = 0
            var blue = 0
            var green = 0
            zs.forEach { colour ->
                val ws = colour.trim().split(' ')
                val name = ws[1].trim().lowercase()
                val count = ws[0].trim().toInt()
                when (name.trim()) {
                    "red" -> red = count
                    "green" -> green = count
                    "blue" -> blue = count
                }
            }
            Reveal(red, green, blue)
        }
        Game(id, reveals)
    }
}
