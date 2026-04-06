package com.compose.offline.expencetracker

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun isToday(timestamp: Long): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance().apply { timeInMillis = timestamp }

    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

fun isThisWeek(timestamp: Long): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance().apply { timeInMillis = timestamp }

    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)
}

fun isThisMonth(timestamp: Long): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance().apply { timeInMillis = timestamp }

    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
}

fun Long.toDay(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(this))
}

fun getDayLabel(offset: Int): String {
    val cal = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, offset) }
    val sdf = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
    return sdf.format(cal.time)
}

fun getWeekLabel(offset: Int): String {
    val cal = Calendar.getInstance().apply { add(Calendar.WEEK_OF_YEAR, offset) }
    val weekStart = cal.clone() as Calendar
    weekStart.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
    val weekEnd = cal.clone() as Calendar
    weekEnd.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek + 6)
    val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
    return "${sdf.format(weekStart.time)} - ${sdf.format(weekEnd.time)}"
}

fun getMonthLabel(offset: Int): String {
    val cal = Calendar.getInstance().apply { add(Calendar.MONTH, offset) }
    val sdf = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    return sdf.format(cal.time)
}

fun isDayOffset(timestamp: Long, offset: Int): Boolean {
    val cal = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, offset) }
    val target = Calendar.getInstance().apply { timeInMillis = timestamp }
    return cal.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
            cal.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)
}

fun isWeekOffset(timestamp: Long, offset: Int): Boolean {
    val cal = Calendar.getInstance().apply { add(Calendar.WEEK_OF_YEAR, offset) }
    val target = Calendar.getInstance().apply { timeInMillis = timestamp }
    return cal.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
            cal.get(Calendar.WEEK_OF_YEAR) == target.get(Calendar.WEEK_OF_YEAR)
}

fun isMonthOffset(timestamp: Long, offset: Int): Boolean {
    val cal = Calendar.getInstance().apply { add(Calendar.MONTH, offset) }
    val target = Calendar.getInstance().apply { timeInMillis = timestamp }
    return cal.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
            cal.get(Calendar.MONTH) == target.get(Calendar.MONTH)
}

fun getFormattedDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}