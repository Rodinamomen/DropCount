package com.example.stayhydratedapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDAO {
    @Insert
     fun insertRecord(record:Record)
     @Query("SELECT * FROM Record")
     fun getRecords(): List<Record>
}