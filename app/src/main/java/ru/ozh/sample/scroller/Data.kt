package ru.ozh.sample.scroller

import kotlin.random.Random

val stringList
    get() = List(300) {
        getRandomString()
            .toLowerCase()
            .capitalize()
    }
        .sorted()

fun getRandomString(): String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvwxyz"
    val wordLength = Random.nextInt(4, 10)
    return (1..wordLength)
        .map { Random.nextInt(0, charset.length) }
        .map(charset::get)
        .joinToString("")
}