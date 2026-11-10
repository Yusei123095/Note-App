package com.yu.my_note_app.ui.editor.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.yu.my_note_app.ui.components.SingleLineTextField
import com.yu.my_note_app.ui.editor.AlarmForm
import com.yu.my_note_app.ui.home.rememberTimerState

@Composable
fun AlarmFormPanel(modifier: Modifier= Modifier,
                   alarmForm: AlarmForm,
                   onTriggerAtChange: (Long) -> Unit,
                   onMemoChange: (String) -> Unit
){
    val initialMillis by
        rememberSaveable { mutableStateOf(alarmForm.triggerAt) }
    val timerState = rememberTimerState(initialMillis)

    val currentOnTriggerAtChange by rememberUpdatedState(onTriggerAtChange)
    LaunchedEffect(timerState.epochMillis) {

        timerState.epochMillis?.let { currentOnTriggerAtChange(it) }

    }

    Column(modifier
        .verticalScroll(rememberScrollState())){
        SingleLineTextField(
            text = alarmForm.memo,
            onChangeText = onMemoChange,
            label = "Memo"
        )
        TimerInput(timerState)
    }

}