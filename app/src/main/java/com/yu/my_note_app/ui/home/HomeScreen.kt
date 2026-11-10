package com.yu.my_note_app.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yu.my_note_app.ui.components.Header
import com.yu.my_note_app.ui.editor.EditorType
import com.yu.my_note_app.ui.home.components.EmptyNoteList
import com.yu.my_note_app.ui.home.components.NoteList

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               onEditor: (id: String?, type: EditorType) -> Unit,
              viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
){
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditor(null, EditorType.NOTE)
                }
            )
            {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding),
            ) {
            Header()
            if (homeUiState.alarms.isNotEmpty() || homeUiState.notes.isNotEmpty())
                NoteList(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    alarms = homeUiState.alarms,
                    notes = homeUiState.notes,
                    onClick = onEditor
                )
            else
                EmptyNoteList(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
        }

    }



}

