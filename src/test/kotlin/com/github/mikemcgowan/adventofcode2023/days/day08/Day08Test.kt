package com.github.mikemcgowan.adventofcode2023.days.day08

import org.jline.terminal.Terminal
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class Day08Test {
    private val terminal: Terminal = Mockito.mock(Terminal::class.java)
    private val input1 = listOf(
        "RL",
        "AAA = (BBB, CCC)",
        "BBB = (DDD, EEE)",
        "CCC = (ZZZ, GGG)",
        "DDD = (DDD, DDD)",
        "EEE = (EEE, EEE)",
        "GGG = (GGG, GGG)",
        "ZZZ = (ZZZ, ZZZ)",
    )
    private val input2 = listOf(
        "LLR",
        "AAA = (BBB, BBB)",
        "BBB = (AAA, ZZZ)",
        "ZZZ = (ZZZ, ZZZ)",
    )
    private val input3 = listOf(
        "LR",
        "11A = (11B, XXX)",
        "11B = (XXX, 11Z)",
        "11Z = (11B, XXX)",
        "22A = (22B, XXX)",
        "22B = (22C, 22C)",
        "22C = (22Z, 22Z)",
        "22Z = (22B, 22B)",
        "XXX = (XXX, XXX)",
    )

    @Test
    fun part1() {
        Assertions.assertEquals(2, Day08(terminal).part1(input1))
        Assertions.assertEquals(6, Day08(terminal).part1(input2))
    }

    @Test
    fun part2() {
        Assertions.assertEquals(6, Day08(terminal).part2(input3))
    }
}
