package com.github.mikemcgowan.adventofcode2023.days.day05

import org.jline.terminal.Terminal
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class Day05Test {
    private val terminal: Terminal = Mockito.mock(Terminal::class.java)
    private val input = listOf(
        "seeds: 79 14 55 13",
        "",
        "seed-to-soil map:",
        "50 98 2",
        "52 50 48",
        "",
        "soil-to-fertilizer map:",
        "0 15 37",
        "37 52 2",
        "39 0 15",
        "",
        "fertilizer-to-water map:",
        "49 53 8",
        "0 11 42",
        "42 0 7",
        "57 7 4",
        "",
        "water-to-light map:",
        "88 18 7",
        "18 25 70",
        "",
        "light-to-temperature map:",
        "45 77 23",
        "81 45 19",
        "68 64 13",
        "",
        "temperature-to-humidity map:",
        "0 69 1",
        "1 0 69",
        "",
        "humidity-to-location map:",
        "60 56 37",
        "56 93 4",
    )

    @Test
    fun lines() {
        val lines = Day05.Lines(
            listOf(
                Day05.Line(50, 98, 2),
                Day05.Line(52, 50, 48),
            )
        )
        Assertions.assertEquals(81, lines.map(79))
        Assertions.assertEquals(14, lines.map(14));
        Assertions.assertEquals(57, lines.map(55));
        Assertions.assertEquals(13, lines.map(13));
    }

    @Test
    fun part1() {
        Assertions.assertEquals(35, Day05(terminal).part1(input))
    }

    @Test
    fun part2() {
        Assertions.assertEquals(46, Day05(terminal).part2(input))
    }
}
