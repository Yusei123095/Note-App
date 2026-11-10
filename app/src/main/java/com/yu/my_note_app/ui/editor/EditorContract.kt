package com.yu.my_note_app.ui.editor

import androidx.annotation.Keep
import com.yu.my_note_app.data.alarm.Alarm
import com.yu.my_note_app.data.note.Note
import com.yu.my_note_app.ui.editor.forms.AlarmForm
import com.yu.my_note_app.ui.editor.forms.NoteForm

sealed interface EditorUiState {
    data object Loading : EditorUiState
    data class Ready(
        val mode: EditorMode = EditorMode.Create,
        val selected: EditorType = EditorType.NOTE,
        val note: NoteForm = NoteForm(),
        val alarm: AlarmForm = AlarmForm(),
        val submitted: Boolean = false,
        val isSaving: Boolean = false,
        val isSaved: Boolean = false
    ) : EditorUiState {
        val errors: Set<EditorError>
            get() = when (selected) {
                EditorType.NOTE -> note.validate()
                EditorType.ALARM -> alarm.validate()
            }
        val visibleErrors: Set<EditorError>
            get() = if (submitted) errors else emptySet()
    }
}

sealed class EditorError(val message: String) {
    data object ContentBlank : EditorError("本文を入力してください")
    data object DateNotSelected : EditorError("日付を選択してください")
    data object PastTime : EditorError("過去の時刻は設定できません")
}

sealed interface EditorEvent {
    data class EditNoteTitle(val title: String): EditorEvent
    data class EditNoteText(val content: String): EditorEvent
    data class EditAlarmText(val memo: String) : EditorEvent
    data class EditAlarmTrigger(val millis: Long) : EditorEvent
    data class ChangeType(val type: EditorType): EditorEvent
}

sealed interface EditorMode {
    data object Create : EditorMode
    data class EditNote(val original: Note) : EditorMode
    data class EditAlarm(val original: Alarm) : EditorMode
}

@Keep
enum class EditorType {
    ALARM,
    NOTE
}