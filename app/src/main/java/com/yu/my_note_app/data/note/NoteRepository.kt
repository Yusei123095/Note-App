package com.yu.my_note_app.data.note

import androidx.sqlite.SQLiteException
import com.yu.my_note_app.data.SaveResult
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao){
    fun observeAllNotes(): Flow<List<Note>> = dao.observeAllNotes()

    suspend fun getNoteById(id: String): Note = dao.getNoteById(id)

    suspend fun insertNote(note: Note): SaveResult = try{
        dao.insert(note)
        SaveResult.Success
    } catch (e: SQLiteException){
        SaveResult.Failure
    }

    suspend fun updateNote(note: Note): SaveResult = try{
        if (dao.update(note) > 0 ) SaveResult.Success else SaveResult.NoteFound
    } catch (e: SQLiteException){
        SaveResult.Failure
    }
}