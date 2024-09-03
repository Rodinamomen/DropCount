package com.example.stayhydratedapp.tracker.repo

import com.example.stayhydratedapp.database.Record

interface TrackerRepo {
    suspend fun insertRecord(record: Record)
    suspend fun getRecords() :List<Record>

}