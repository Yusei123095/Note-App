package com.yu.my_note_app.data.note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao{
    @Query("select * from notes")
    fun observeAllNotes(): Flow<List<Note>>

    @Query("select * from notes where id = :id")
    suspend fun getNoteById(id: String): Note

    @Insert
    suspend fun insert(note: Note)

    @Insert
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note): Int
}