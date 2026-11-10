package com.yu.my_note_app.ui.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yu.my_note_app.ui.components.Header
import com.yu.my_note_app.ui.editor.components.AlarmFormPanel
import com.yu.my_note_app.ui.editor.components.NoteFormPanel
import com.yu.my_note_app.ui.editor.components.TypeToggleButton

@Composable
fun EditorScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    editorViewModel: EditorViewModel = viewModel(factory = EditorViewModel.Factory)
) {
    val state by editorViewModel.uiState.collectAsState()
    val headerText = when (val s = state) {
        is EditorUiState.Ready -> when (s.mode) {
            EditorMode.Create -> "新規作成"
            is EditorMode.Edit -> when (s.selected) {
                EditorType.NOTE -> "メモを編集"
                EditorType.ALARM -> "アラームを編集"
            }
        }
        else -> "読み込み中"
    }

    LaunchedEffect(state) {
        when (val s = state) {
            is EditorUiState.Ready ->
                if (s.isSaved)
                    onBack()
                else return@LaunchedEffect
            else -> return@LaunchedEffect
        }

    }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editorViewModel.save()
                }
            ) {
                Icon(Icons.Default.Done, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)) {
            Header(title = headerText, suffixIcon = Icons.Default.Clear, suffixIconOnPush = onBack)

            when(val s = state){

                // Tab bar
                is EditorUiState.Ready -> {
                    if (s.mode is EditorMode.Create)
                        Row(Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                            // Call Note
                            TypeToggleButton(
                                label = "Note",
                                selected = s.selected == EditorType.NOTE,
                                onClick = {
                                    editorViewModel.manageForm(
                                        EditorEvent.ChangeType(type = EditorType.NOTE)
                                    )
                                }
                            )

                            // Call Alarm
                            TypeToggleButton(
                                label = "Alarm",
                                selected = s.selected == EditorType.ALARM,
                                onClick = {
                                    editorViewModel.manageForm(
                                        EditorEvent.ChangeType(type = EditorType.ALARM)
                                    )
                                }

                            )
                        }

                    // error statement
                    s.visibleErrors.forEach { err ->
                        Text(err.message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
                    }

                    // Forms
                    when (s.selected){
                        EditorType.NOTE -> {
                            NoteFormPanel(
                                modifier = Modifier.padding(vertical = 8.dp),
                                note = s.note,
                                onTitleChange = { title ->
                                    editorViewModel.manageForm(
                                        EditorEvent.EditNoteTitle(title)
                                    )
                                },

                                onTextChanged = { text ->
                                    editorViewModel.manageForm(
                                        EditorEvent.EditNoteText(text)
                                    )
                                })
                        }

                        EditorType.ALARM -> {
                            AlarmFormPanel(
                                modifier = Modifier.padding(vertical = 8.dp),
                                alarmForm = s.alarm,
                                onMemoChange = { memo ->
                                    editorViewModel.manageForm(
                                        EditorEvent.EditAlarmText(memo)
                                    )

                                },
                                onTriggerAtChange = { millis ->
                                    editorViewModel.manageForm(
                                        EditorEvent.EditAlarmTrigger(millis)
                                    )

                                }
                            )

                        }
                    }

                }
                else -> {}
            }






        }
    }


}