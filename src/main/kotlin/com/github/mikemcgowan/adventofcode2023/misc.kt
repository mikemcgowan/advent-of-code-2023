package com.github.mikemcgowan.adventofcode2023

import org.springframework.core.io.Resource
import java.io.BufferedReader

fun resourceToLines(r: Resource) =
    r.inputStream.bufferedReader().use(BufferedReader::readText).split('\n').filter { it.isNotEmpty() }
