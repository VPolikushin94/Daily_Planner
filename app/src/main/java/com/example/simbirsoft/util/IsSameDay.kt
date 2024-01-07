package com.example.simbirsoft.util

import java.util.Calendar

fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
    return calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) &&
           calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
}