package com.yu.my_note_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.yu.my_note_app.NoteApplication
import com.yu.my_note_app.data.alarm.Alarm
import com.yu.my_note_app.data.alarm.AlarmRepository
import com.yu.my_note_app.data.note.Note
import com.yu.my_note_app.data.note.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


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


    val homeUiState: StateFlow<HomeUiState> = combine(
        noteRepo.observeAll(), alarmRepo.observeUpcoming()
    ){notes, alarms ->
        HomeUiState(
            alarms = alarms,
            notes = notes,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )


    fun homeEvent(event: HomeContract) {

    }



}