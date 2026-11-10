package com.yu.my_note_app.ui.home

import com.yu.my_note_app.data.note.AlarmNote
import com.yu.my_note_app.data.note.SimpleNote

sealed interface HomeEvent{
    data object openNoteDialog : HomeEvent
    data object closeNoteDialog : HomeEvent
    data class createSimpleNote(val note: SimpleNote) : HomeEvent
    data class createAlarmNote(val note: AlarmNote) : HomeEvent
}