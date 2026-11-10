package com.yu.my_note_app.ui.editor.components

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
object FutureDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val todayUtc = LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        return utcTimeMillis >= todayUtc
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class TimerState(
    val dateState: DatePickerState,
    val timeState: TimePickerState,
    private val initialMillis: Long?
) {
    // 時刻（読み書きできる）
    var time: LocalTime
        get() = LocalTime.of(timeState.hour, timeState.minute)
        set(value) { timeState.hour = value.hour; timeState.minute = value.minute }

    // 日付（DatePicker から計算）
    val date: LocalDate?
        get() = dateState.selectedDateMillis?.let {
            Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate()
        }

    // 日付 ＋ 時刻
    val dateTime: LocalDateTime?
        get() = date?.let { LocalDateTime.of(it, time) }

    // 保存用の Long
    val epochMillis: Long?
        get() = dateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()

    // 過去の時刻かどうか
    val isPast: Boolean
        get() = dateTime?.isBefore(LocalDateTime.now()) ?: false

    fun setTime(hour: Int, minute: Int) {
        time = LocalTime.of(hour, minute)
    }

    fun reset() {
        val target = initialMillis?.toLocalDateTime() ?: defaultDateTime()
        val picker = target.toMillis()
        dateState.selectedDateMillis = picker
        dateState.displayedMonthMillis = picker
        time = target.toLocalTime()
    }
}

fun defaultDateTime(): LocalDateTime =
    LocalDateTime.now().plusMinutes(2).withSecond(0).withNano(0)

private fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

fun LocalDateTime.toMillis(): Long =
    this.atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberTimerState(initialMillis: Long? = null): TimerState {

    val datetime = initialMillis?.toLocalDateTime() ?: defaultDateTime()
    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = datetime.toMillis(),
        yearRange = LocalDate.now().year..LocalDate.now().year + 1,
        selectableDates = FutureDates
    )

    val timeState = rememberTimePickerState(
        initialHour = datetime.hour,
        initialMinute = datetime.minute,
        is24Hour = true
    )

    return remember(dateState, timeState) { TimerState(dateState, timeState, initialMillis) }
}