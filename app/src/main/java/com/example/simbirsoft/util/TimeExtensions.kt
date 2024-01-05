package com.example.simbirsoft.util

fun Long.toTimestamp(): Long {
    return this / 1000
}

fun Long.toMillis(): Long {
    return this * 1000
}