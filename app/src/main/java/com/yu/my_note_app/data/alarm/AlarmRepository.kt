package com.yu.my_note_app.data.alarm


import android.util.Log
import androidx.sqlite.SQLiteException
import com.yu.my_note_app.data.SaveResult
import kotlinx.coroutines.flow.Flow

class AlarmRepository(private val dao: AlarmDao) {
    fun observeAll(): Flow<List<Alarm>> = dao.observeAll()
    fun observeUpcoming(num: Int = 3): Flow<List<Alarm>> =
        dao.observeUpcomingAlarms(num)

    suspend fun getAlarmById(id: String): Alarm = dao.getAlarmById(id)

    suspend fun insertAlarm(alarm: Alarm): SaveResult = try {
        dao.insert(alarm)
        SaveResult.Success
    } catch (e: SQLiteException){
        Log.e("NoteRepo", "insert failed", e)
        SaveResult.Failure
    }

    suspend fun updateAlarm(alarm: Alarm): SaveResult = try {
        if (dao.update(alarm) > 0) SaveResult.Success else SaveResult.NoteFound
    } catch (e: SQLiteException){
        Log.e("NoteRepo", "insert failed", e)
        SaveResult.Failure
    }


}