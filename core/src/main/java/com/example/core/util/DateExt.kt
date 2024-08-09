package com.example.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.example.core.R

@Composable
fun Long.getTimeDiffLabel(): String = when {
    this > 3_1104_000L -> pluralStringResource(
        id = R.plurals.years_ago,
        count = (this / 3_1104_000L).toInt(),
        (this / 3_1104_000L).toInt()
    )
    this > 2_592_000L -> pluralStringResource(
        id = R.plurals.months_ago,
        count = (this / 2_592_000L).toInt(),
        (this / 2_592_000L).toInt()
    )
    this > 86_400L -> pluralStringResource(
        id = R.plurals.days_ago,
        count = (this / 86_400L).toInt(),
        (this / 86_400L).toInt()
    )
    this > 3_600L -> pluralStringResource(
        id = R.plurals.hours_ago,
        count = (this / 3_600L).toInt(),
        (this / 3_600L).toInt()
    )
    this > 60L -> pluralStringResource(
        id = R.plurals.minutes_ago,
        count = (this / 60L).toInt(),
        (this / 60L).toInt()
    )
    else -> pluralStringResource(
        id = R.plurals.seconds_ago,
        count = this.toInt(),
        (this / 60L).toInt()
    )
}