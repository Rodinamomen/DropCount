package com.example.stayhydratedapp.database

import android.content.Context
class LocalDatabaseImp(context: Context) :LocalDatabase {
    private var recordDAO: RecordDAO
    init {
        val db = RecordsDatabase.getInstance(context)
        recordDAO = db.recordDAO()
    }
    override suspend fun insertRecord(record: Record) {
             return recordDAO.insertRecord(record)
    }

    override suspend fun getRecords(): List<Record> {
        return recordDAO.getRecords()
    }
}