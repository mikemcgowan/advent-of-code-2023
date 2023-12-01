package com.github.mikemcgowan.adventofcode2023.days.day01

import org.jline.terminal.Terminal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class Day01Test {
    private val terminal: Terminal = mock(Terminal::class.java)

    @Test
    fun part1() {
        val input = listOf(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet",
        )
        assertEquals(142, Day01(terminal).part1(input))
    }

    @Test
    fun part2() {
        val input = listOf(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen",
        )
        assertEquals(281, Day01(terminal).part2(input))
    }
}
