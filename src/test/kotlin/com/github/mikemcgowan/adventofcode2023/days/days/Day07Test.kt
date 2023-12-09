package com.github.mikemcgowan.adventofcode2023.days.days

import org.jline.terminal.Terminal
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class Day07Test {
    private val terminal: Terminal = Mockito.mock(Terminal::class.java)
    private val input = listOf(
        "32T3K 765",
        "T55J5 684",
        "KK677 28",
        "KTJJT 220",
        "QQQJA 483",
    )

    @Test
    fun handType() {
        val fiveOfAKind = Day07.Hand(listOf(14, 14, 14, 14, 14), 0)
        Assertions.assertEquals(Day07.HandType.FIVE_OF_A_KIND, fiveOfAKind.handType)

        val fourOfAKind = Day07.Hand(listOf(14, 14, 8, 14, 14), 0)
        Assertions.assertEquals(Day07.HandType.FOUR_OF_A_KIND, fourOfAKind.handType)
        Assertions.assertTrue(fiveOfAKind > fourOfAKind)

        val fullHouse = Day07.Hand(listOf(2, 3, 3, 2), 0)
        Assertions.assertEquals(Day07.HandType.FULL_HOUSE, fullHouse.handType)
        Assertions.assertTrue(fourOfAKind > fullHouse)

        val threeOfAKind = Day07.Hand(listOf(10, 10, 10, 9, 8), 0)
        Assertions.assertEquals(Day07.HandType.THREE_OF_A_KIND, threeOfAKind.handType)
        Assertions.assertTrue(fullHouse > threeOfAKind)

        val twoPair = Day07.Hand(listOf(2, 3, 4, 3, 2), 0)
        Assertions.assertEquals(Day07.HandType.TWO_PAIR, twoPair.handType)
        Assertions.assertTrue(threeOfAKind > twoPair)

        val onePair = Day07.Hand(listOf(14, 2, 3, 14, 4), 0)
        Assertions.assertEquals(Day07.HandType.ONE_PAIR, onePair.handType)
        Assertions.assertTrue(twoPair > onePair)

        val highCard = Day07.Hand(listOf(2, 3, 4, 5, 6), 0)
        Assertions.assertEquals(Day07.HandType.HIGH_CARD, highCard.handType)
        Assertions.assertTrue(onePair > highCard)
    }

    @Test
    fun compareByCards() {
        var hand1 = Day07.Hand(listOf(3, 3, 3, 3, 2), 0)
        var hand2 = Day07.Hand(listOf(2, 14, 14, 14, 14), 0)
        Assertions.assertTrue(hand1 > hand2)

        hand1 = Day07.Hand(listOf(7, 7, 8, 8, 8), 0)
        hand2 = Day07.Hand(listOf(7, 7, 7, 8, 8), 0)
        Assertions.assertTrue(hand1 > hand2)
    }

    @Test
    fun part1() {
        Assertions.assertEquals(6440, Day07(terminal).part1(input))
    }

    @Test
    fun part2() {
        Assertions.assertEquals(0, Day07(terminal).part2(input))
    }
}
