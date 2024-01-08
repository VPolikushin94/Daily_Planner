package com.example.simbirsoft.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toTimestamp(): Long {
    return this / 1000
}

fun Long.toMillis(): Long {
    return this * 1000
}

fun Date.getFormatString(): String {
    val formatter = SimpleDateFormat("d MMM H:mm", Locale.getDefault())
    return formatter.format(this)
}

fun Date.getFormatStringWithYear(): String {
    val formatter = SimpleDateFormat("d MMM y H:mm", Locale.getDefault())
    return formatter.format(this)
}