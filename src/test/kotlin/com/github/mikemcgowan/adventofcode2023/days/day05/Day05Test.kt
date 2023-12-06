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
    fun part1() {
        Assertions.assertEquals(35, Day05(terminal).part1(input))
    }

    @Test
    fun part2() {
        Assertions.assertEquals(46, Day05(terminal).part2(input))
    }

    @Test
    fun mapRange() {
        val line = Day05.Line(100, 10, 10)

        // given range lies entirely 'below' line's source range
        Assertions.assertEquals(
            Triple(null, 1L..9L, null),
            line.mapRange(1L..9L)
        )

        // given range lies entirely 'above' line's source range
        Assertions.assertEquals(
            Triple(null, null, 20L..29L),
            line.mapRange(20L..29L)
        )

        // given range lies entirely within line's source range
        Assertions.assertEquals(
            Triple(102L..107L, null, null),
            line.mapRange(12L..17L)
        )

        // line's source range lies entirely within given range
        Assertions.assertEquals(
            Triple(100L..109L, 1L..9L, 20L..29L),
            line.mapRange(1L..29L)
        )

        // given range lies partially 'below' line's source range
        Assertions.assertEquals(
            Triple(100L..107L, 1L..9L, null),
            line.mapRange(1L..17L)
        )

        // given range lies partially 'above' line's source range
        Assertions.assertEquals(
            Triple(107L..109L, null, 20L..29L),
            line.mapRange(17L..29L)
        )
    }
}
