package com.github.mikemcgowan.adventofcode2023.days.day09

import org.jline.terminal.Terminal
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class Day09Test {
    private val terminal: Terminal = Mockito.mock(Terminal::class.java)
    private val input = listOf(
        "0 3 6 9 12 15",
        "1 3 6 10 15 21",
        "10 13 16 21 30 45",
    )

    @Test
    fun part1() {
        Assertions.assertEquals(114, Day09(terminal).part1(input))
    }

    @Test
    fun part2() {
        Assertions.assertEquals(2, Day09(terminal).part2(input))
    }
}
