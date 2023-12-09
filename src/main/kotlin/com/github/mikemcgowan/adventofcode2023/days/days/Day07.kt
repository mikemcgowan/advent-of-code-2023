package com.github.mikemcgowan.adventofcode2023.days.days

import com.github.mikemcgowan.adventofcode2023.days.BaseDay
import org.jline.terminal.Terminal
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class Day07(terminal: Terminal) : BaseDay(terminal) {
    @Value("classpath:days/day07/input.txt")
    lateinit var input: Resource

    enum class HandType {
        FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD,
    }

    data class Hand(val cards: List<Int>, val bid: Int, val jokers: Boolean = false) : Comparable<Hand> {
        val handType: HandType

        init {
            val groups = if (jokers && cards.contains(1)) groupUsingJokers() else cards.groupBy { it }
            handType = when {
                groups.size == 1 -> HandType.FIVE_OF_A_KIND
                groups.size == 2 && groups.any { it.value.size == 4 } -> HandType.FOUR_OF_A_KIND
                groups.size == 2 && groups.all { it.value.size >= 2 } -> HandType.FULL_HOUSE
                groups.size == 3 && groups.any { it.value.size == 3 } -> HandType.THREE_OF_A_KIND
                groups.size == 3 && groups.count { it.value.size == 2 } == 2 -> HandType.TWO_PAIR
                groups.size == 4 && groups.any { it.value.size == 2 } -> HandType.ONE_PAIR
                else -> HandType.HIGH_CARD
            }
        }

        override fun compareTo(other: Hand): Int =
            if (this.handType == other.handType) compareByCards(other) else compareByHandType(other)

        private fun compareByCards(other: Hand): Int {
            val xs = this.cards.zip(other.cards).dropWhile { it.first == it.second }.first()
            return xs.first.compareTo(xs.second)
        }

        private fun compareByHandType(other: Hand): Int =
            Comparator.comparingInt<Hand> { HandType.HIGH_CARD.ordinal - it.handType.ordinal }.compare(this, other)

        private fun groupUsingJokers(): Map<Int, List<Int>> {
            val groups = cards.groupBy { it }.toMutableMap()
            val jokerCount = groups[1]?.size ?: 0
            if (jokerCount == cards.size) return mapOf(14 to List(cards.size) { 14 }) // edge case of all Jokers
            groups.remove(1)
            val group = groups.maxBy { it.value.size }
            groups.computeIfPresent(group.key) { a, b -> b.plus(List(jokerCount) { a }) }
            return groups
        }
    }

    @ShellMethod("Day 7")
    fun day7(
        @ShellOption(defaultValue = "false") skipPart1: Boolean,
        @ShellOption(defaultValue = "false") skipPart2: Boolean
    ) {
        run(input, skipPart1, skipPart2)
    }

    override fun part1(lines: List<String>): Long =
        parse(lines).sorted().mapIndexed { index, hand -> (index + 1) * hand.bid }.sum().toLong()

    override fun part2(lines: List<String>): Long =
        parse(lines, true).sorted().mapIndexed { index, hand -> (index + 1) * hand.bid }.sum().toLong()

    private fun parse(lines: List<String>, part2: Boolean = false): List<Hand> =
        lines.map { line ->
            val xs = line.split(' ')
            val cards = xs[0].toCharArray().map { card ->
                when (card) {
                    in '2'..'9' -> card.toString().toInt()
                    'T' -> 10
                    'J' -> if (part2) 1 else 11
                    'Q' -> 12
                    'K' -> 13
                    'A' -> 14
                    else -> 0
                }
            }
            val bid = xs[1].toInt()
            Hand(cards, bid, part2)
        }
}
