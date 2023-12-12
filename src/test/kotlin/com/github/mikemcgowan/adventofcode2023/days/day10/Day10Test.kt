package com.github.mikemcgowan.adventofcode2023.days.day10

import org.jline.terminal.Terminal
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class Day10Test {
    private val terminal: Terminal = Mockito.mock(Terminal::class.java)
    private val input1 = listOf(
        ".....",
        ".S-7.",
        ".|.|.",
        ".L-J.",
        ".....",
    )
    private val input2 = listOf(
        "..F7.",
        ".FJ|.",
        "SJ.L7",
        "|F--J",
        "LJ...",
    )

    @Test
    fun part1() {
        Assertions.assertEquals(4, Day10(terminal).part1(input1))
        Assertions.assertEquals(8, Day10(terminal).part1(input2))
    }

    @Test
    fun part2() {
        Assertions.assertEquals(0, Day10(terminal).part2(input1))
    }
}
