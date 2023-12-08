package com.github.mikemcgowan.adventofcode2023.days.day06

import org.jline.terminal.Terminal
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class Day06Test {
    private val terminal: Terminal = Mockito.mock(Terminal::class.java)
    private val input = listOf(
        "Time:      7  15   30",
        "Distance:  9  40  200",
    )

    @Test
    fun part1() {
        Assertions.assertEquals(288, Day06(terminal).part1(input))
    }

    @Test
    fun part2() {
        Assertions.assertEquals(0, Day06(terminal).part2(input))
    }
}
