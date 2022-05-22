package com.visa.timesreader.utils

import androidx.databinding.ViewDataBinding
import com.visa.timesreader.R
import java.time.Duration
import java.time.OffsetDateTime

class DateTimeParser {

    /**
     * Return time between date and current time in human readable format
     *
     * Format with string resource to add appropriate suffix
     */
    fun timeBetween(date: String, binding: ViewDataBinding): String {
        val time = OffsetDateTime.parse(date).toLocalDateTime()
        val currentTime = OffsetDateTime.now().toLocalDateTime()
        val d = Duration.between(time, currentTime)

        return if (d.toDays() < 1) {
            if (d.toHours() < 1) {
                binding.root.context.getString(R.string.minutes_ago, d.toMinutes().toString())
            } else {
                binding.root.context.getString(R.string.hours_ago, d.toHours().toString())
            }
        } else {
            binding.root.context.getString(R.string.days_ago, d.toDays().toString())
        }
    }
}