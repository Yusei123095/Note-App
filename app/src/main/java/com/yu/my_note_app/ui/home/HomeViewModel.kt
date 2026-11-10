package com.yu.my_note_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.yu.my_note_app.NoteApplication
import com.yu.my_note_app.data.alarm.Alarm
import com.yu.my_note_app.data.alarm.AlarmRepository
import com.yu.my_note_app.data.note.Note
import com.yu.my_note_app.data.note.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class HomeState(
    val alarmList: List<Alarm> = listOf(),
    val noteList: List<Note> = listOf(),
    val showDialog: Boolean = false
)

class HomeViewModel(private val noteRepo: NoteRepository, alarmRepo: AlarmRepository): ViewModel(){

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as NoteApplication
                HomeViewModel(
                    noteRepo = app.noteRepository,
                    alarmRepo = app.alarmRepository
                    )
            }
        }
    }

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()


    fun homeEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.openNoteDialog -> {
                _homeState.update { it.copy(showDialog = true) }
            }

            HomeEvent.closeNoteDialog -> {
                _homeState.update { it.copy(showDialog = false) }
            }

            is HomeEvent.createSimpleNote -> {
            }

            else -> {}
        }
    }



}