package com.imasha.hydrateme.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imasha.hydrateme.data.model.Record

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: Record)

    @Query("SELECT * FROM record WHERE user = :user")
    suspend fun getRecords(user: String): List<Record>?

    @Query("SELECT * FROM record WHERE user = :user AND date = :date")
    suspend fun getTodayRecords(user: String, date: String): List<Record>?
}