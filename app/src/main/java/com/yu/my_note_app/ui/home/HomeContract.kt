package com.yu.my_note_app.ui.home

import com.yu.my_note_app.data.alarm.Alarm
import com.yu.my_note_app.data.note.Note


data class HomeUiState(
    val alarms: List<Alarm> = listOf(),
    val notes: List<Note> = listOf(),
)

sealed interface HomeContract{
}