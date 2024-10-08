package com.example.transactions.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {

    val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun formatStringDate(dateToFormat: LocalDate?): String {
        return dateToFormat?.let {
            dateFormat.format(dateToFormat)
        } ?: "Add Date"
    }

    fun retrieveLocalDateFromInstant(updatedDate: Long): LocalDate {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(updatedDate), ZoneId.systemDefault()).toLocalDate()
    }
}