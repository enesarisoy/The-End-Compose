package com.ns.theendcompose.utils

import java.text.NumberFormat
import java.util.*
import org.joda.time.Duration
import org.joda.time.DurationFieldType
import org.joda.time.Period
import org.joda.time.format.PeriodFormatterBuilder

fun Int.formattedRuntime(): String? {
    val period = Period.minutes(this)
    val hours = period.get(DurationFieldType.hours())
    val minutes = period.get(DurationFieldType.minutes())

    val formatter = PeriodFormatterBuilder()
        .appendHours()
        .appendSuffix("h")
        .appendSeparator(" ")
        .appendMinutes()
        .appendSuffix("m")
        .toFormatter()

    val formatted = formatter.print(period)
    return if (hours == 0 && minutes == 0) null else formatted
}



/*import kotlin.time.Duration.Companion.minutes

fun Int.formattedRuntime(): String? {
    return minutes.toComponents { hours, minutes, _, _ ->
        val hoursString = if (hours > 0) "${hours}h" else null
        val minutesString = if (minutes > 0) "${minutes}m" else null

        listOfNotNull(hoursString, minutesString).run {
            if (isEmpty()) null else joinToString(separator = " ")
        }
    }
}*/

fun Float.singleDecimalPlaceFormatted(): String {
    return String.format("%.1f", this)
}

fun Long.formattedMoney(): String {
    return NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }.format(this).replace(",", " ")
}