package com.yu.my_note_app.ui.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yu.my_note_app.NoteApplication
import com.yu.my_note_app.data.note.NoteRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yu.my_note_app.Editor
import com.yu.my_note_app.data.SaveResult
import com.yu.my_note_app.data.alarm.Alarm
import com.yu.my_note_app.data.alarm.AlarmRepository
import com.yu.my_note_app.data.note.Note
import com.yu.my_note_app.ui.editor.forms.AlarmForm
import com.yu.my_note_app.ui.editor.forms.NoteForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class EditorViewModel(savedStateHandle: SavedStateHandle,
                      private val noteRepo: NoteRepository,
                      private val alarmRepo: AlarmRepository) : ViewModel(){

    private val route = savedStateHandle.toRoute<Editor>()  //receive id and type

    private val _uiState = MutableStateFlow<EditorUiState>(
        if (route.id == null)EditorUiState.Ready()
        else EditorUiState.Loading)

    val uiState: StateFlow<EditorUiState> = _uiState.asStateFlow()

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory{
            initializer{
                val app = this[APPLICATION_KEY] as NoteApplication

                EditorViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    noteRepo = app.noteRepository,
                    alarmRepo = app.alarmRepository)
            }
        }
    }

    init {
        val id = route.id
        if (id != null) {
            viewModelScope.launch {
                _uiState.value = when (route.type) {
                    EditorType.NOTE -> noteRepo.getNoteById(id).let {
                        EditorUiState.Ready(
                            mode = EditorMode.EditNote(it),
                            selected = EditorType.NOTE,
                            note = NoteForm(title = it.title, content = it.content)
                        )
                    }

                    EditorType.ALARM -> alarmRepo.getAlarmById(id).let {
                        EditorUiState.Ready(
                            mode = EditorMode.EditAlarm(it),
                            selected = EditorType.ALARM,
                            alarm = AlarmForm(memo = it.memo, triggerAt = it.triggerAt)
                        )
                    }
                }
            }
        }
    }

    private inline fun updateReady(transform: (EditorUiState.Ready) -> EditorUiState.Ready) {
        _uiState.update { if (it is EditorUiState.Ready) transform(it) else it }
    }

    fun manageForm(event: EditorEvent) {
        when (event) {
            is EditorEvent.EditNoteTitle -> updateReady {
                it.copy(note = it.note.copy(title = event.title))
            }

            is EditorEvent.EditNoteText -> updateReady {
                it.copy(note = it.note.copy(content = event.content))
            }
            is EditorEvent.EditAlarmText -> updateReady {
                it.copy(alarm = it.alarm.copy(memo = event.memo))
            }
            is EditorEvent.EditAlarmTrigger -> updateReady {
                it.copy(alarm = it.alarm.copy(triggerAt = event.millis))
            }
            is EditorEvent.ChangeType -> updateReady { state ->
                if (state.mode !is EditorMode.Create || state.selected == event.type) return@updateReady state
                when (event.type) {
                    EditorType.ALARM -> state.copy(selected = event.type)
                    EditorType.NOTE -> state.copy(selected = event.type)
                }
            }
        }
    }

    fun save(){
        val state = _uiState.value as? EditorUiState.Ready ?: return

        if (state.isSaving) return
        if (state.errors.isNotEmpty()){
            updateReady { it.copy(submitted = true) }
            return
        }

        updateReady { it.copy(isSaving = true) }
        viewModelScope.launch {
            val res = when (state.mode) {
                is EditorMode.Create -> {
                    when (state.selected) {
                        EditorType.NOTE -> noteRepo.insertNote(state.note.toNew())
                        EditorType.ALARM -> alarmRepo.insertAlarm(state.alarm.toNew())
                    }
                }
                is EditorMode.EditNote -> noteRepo.updateNote(state.note.applyTo(state.mode.original))
                is EditorMode.EditAlarm -> alarmRepo.updateAlarm(state.alarm.applyTo(state.mode.original))
            }

            // Check the save result
            when (res){
                SaveResult.Success -> updateReady { it.copy(isSaving = false, isSaved = true) }
                else -> updateReady { it.copy(isSaving = false) }

            }

        }
    }
}