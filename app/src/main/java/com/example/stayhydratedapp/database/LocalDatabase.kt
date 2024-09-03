package com.example.stayhydratedapp.database

interface LocalDatabase {
    suspend fun insertRecord(record: Record)
    suspend fun getRecords():List<Record>
}