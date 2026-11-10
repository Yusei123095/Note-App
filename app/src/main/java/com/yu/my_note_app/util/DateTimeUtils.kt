package com.yu.my_note_app.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatCreatedAt(millis: Long): String =
    Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))

fun formatTimer(millis: Long): String =
    Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("MM/dd HH:mm"))