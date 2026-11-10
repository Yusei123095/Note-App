package com.yu.my_note_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yu.my_note_app.data.alarm.AlarmDao
import com.yu.my_note_app.data.alarm.Alarm
import com.yu.my_note_app.data.note.NoteDao
import com.yu.my_note_app.data.note.Note

@Database(entities = [Note::class, Alarm::class], version= 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
    abstract fun alarmDao() : AlarmDao
}