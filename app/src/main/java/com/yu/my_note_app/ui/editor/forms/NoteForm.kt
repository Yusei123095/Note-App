package com.yu.my_note_app.ui.editor.forms

import com.yu.my_note_app.data.note.Note
import com.yu.my_note_app.ui.editor.EditorError

data class NoteForm(
    val title: String = "",
    val content: String = ""
) {
    fun validate(): Set<EditorError> = buildSet {
        if (content.isBlank()) add(EditorError.ContentBlank)
    }

    fun toNew(): Note = Note(
        title = this.title,
        content = this.content
    )

    fun applyTo(original: Note): Note = original.copy(
        title = title,
        content = content,
        updatedAt = System.currentTimeMillis()
    )
}