package com.yu.my_note_app.ui.home.addnote

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yu.my_note_app.ui.components.CustomizedPager
import com.yu.my_note_app.ui.home.TimerState
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerInput(timerState: TimerState){
    val timerInputTips: List<@Composable () -> Unit> = listOf(
        { DatePicker(timerState.dateState) },
        {
            TimePicker(
                modifier = Modifier.fillMaxWidth(),
                state =  timerState.timeState

            )
        }
    )

    val interactionSource = remember { MutableInteractionSource() }


    val timerText = timerState.dateTime?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) ?: ""


    Column(Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = timerText,
            onValueChange = {},
            readOnly = true,
            interactionSource = interactionSource,
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
            trailingIcon = {IconButton(
                onClick = {timerState.reset()}
            ) {
                Icon(Icons.Default.ClearAll, contentDescription = "reset")
            }},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (timerState.isPast) Text(modifier = Modifier.padding(horizontal = 3.dp),
            text = "このタイマーは有効ではありません", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error
        )
        CustomizedPager(
            pages = timerInputTips
        )
    }

}