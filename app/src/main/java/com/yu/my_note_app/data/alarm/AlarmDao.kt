package com.yu.my_note_app.data.alarm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yu.my_note_app.data.alarm.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("select * from alarms order by triggerAt asc")
    fun observeAll(): Flow<List<Alarm>>

    @Query("select * from alarms where enable = true order by triggerAt asc limit 1")
    suspend fun getNextAlarm(): Alarm

    @Query("select * from alarms where enable = true order by triggerAt asc limit :limit")
    fun observeUpcomingAlarms(limit: Int) : Flow<List<Alarm>>

    @Query("select * from alarms where id = :id")
    suspend fun getAlarmById(id: String): Alarm

    @Insert
    suspend fun insert(alarm: Alarm)

    @Update
    suspend fun update(alarm: Alarm): Int

}