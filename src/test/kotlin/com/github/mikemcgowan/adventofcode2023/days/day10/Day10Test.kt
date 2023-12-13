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
    private val input3 = listOf(
        "..........",
        ".S------7.",
        ".|F----7|.",
        ".||....||.",
        ".||....||.",
        ".|L-7F-J|.",
        ".|..||..|.",
        ".L--JL--J.",
        "..........",
    )
    private val input4 = listOf(
        ".F----7F7F7F7F-7....",
        ".|F--7||||||||FJ....",
        ".||.FJ||||||||L7....",
        "FJL7L7LJLJ||LJ.L-7..",
        "L--J.L7...LJS7F-7L7.",
        "....F-J..F7FJ|L7L7L7",
        "....L7.F7||L7|.L7L7|",
        ".....|FJLJ|FJ|F7|.LJ",
        "....FJL-7.||.||||...",
        "....L---J.LJ.LJLJ...",
    )
    private val input5 = listOf(
        "FF7FSF7F7F7F7F7F---7",
        "L|LJ||||||||||||F--J",
        "FL-7LJLJ||||||LJL-77",
        "F--JF--7||LJLJ7F7FJ-",
        "L---JF-JLJ.||-FJLJJ7",
        "|F|F-JF---7F7-L7L|7|",
        "|FFJF7L7F-JF7|JL---7",
        "7-L-JL7||F7|L7F-7F7|",
        "L.L7LFJ|||||FJL7||LJ",
        "L7JLJL-JLJLJL--JLJ.L",
    )

    @Test
    fun part1() {
        Assertions.assertEquals(4, Day10(terminal).part1(input1))
        Assertions.assertEquals(8, Day10(terminal).part1(input2))
    }

    @Test
    fun part2() {
        Assertions.assertEquals(4, Day10(terminal).part2(input3))
        Assertions.assertEquals(8, Day10(terminal).part2(input4))
        Assertions.assertEquals(10, Day10(terminal).part2(input5))
    }
}
