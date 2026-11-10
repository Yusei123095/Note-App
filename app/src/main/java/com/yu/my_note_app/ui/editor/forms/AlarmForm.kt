package com.yu.my_note_app.ui.editor.forms

import com.yu.my_note_app.data.alarm.Alarm
import com.yu.my_note_app.ui.editor.EditorError
import com.yu.my_note_app.ui.editor.components.defaultDateTime
import com.yu.my_note_app.ui.editor.components.toMillis

data class AlarmForm(
    val memo: String = "",
    val triggerAt: Long? = null
) {
    fun validate(now: Long = System.currentTimeMillis()): Set<EditorError> = buildSet {
        when {
            triggerAt == null -> add(EditorError.DateNotSelected)
            triggerAt <= now -> add(EditorError.PastTime)
        }
    }

    fun toNew(): Alarm = Alarm(
        memo = this.memo,
        triggerAt = this.triggerAt ?: defaultDateTime().toMillis()
    )

    fun applyTo(original: Alarm): Alarm = original.copy(
        memo = memo,
        triggerAt = triggerAt ?: original.triggerAt,
        updatedAt = System.currentTimeMillis()
    )
}