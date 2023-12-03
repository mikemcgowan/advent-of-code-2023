package com.github.mikemcgowan.adventofcode2023.days.day03

import org.jline.terminal.Terminal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class Day03Test {
    private val terminal: Terminal = Mockito.mock(Terminal::class.java)
    private val input = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598..",
    )

    @Test
    fun part1() {
        assertEquals(4361, Day03(terminal).part1(input))
    }

    @Test
    fun part2() {
        assertEquals(467835, Day03(terminal).part2(input))
    }
}
