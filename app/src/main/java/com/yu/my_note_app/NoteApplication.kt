package com.yu.my_note_app

import android.app.Application
import androidx.room.Room
import com.yu.my_note_app.data.alarm.AlarmRepository
import com.yu.my_note_app.data.AppDatabase
import com.yu.my_note_app.data.note.NoteRepository

class NoteApplication: Application() {
    val database by lazy {
        Room
            .databaseBuilder(this,
                AppDatabase::class.java,
                "app-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val noteRepository by lazy { NoteRepository(database.noteDao()) }
    val alarmRepository by lazy { AlarmRepository(database.alarmDao()) }
}