package com.example.stayhydratedapp.tracker.repo

import com.example.stayhydratedapp.database.LocalDatabase
import com.example.stayhydratedapp.database.Record

class TrackerRepoImp(val localDatabase: LocalDatabase): TrackerRepo {
   override  suspend fun insertRecord(record: Record){
        localDatabase.insertRecord(record)
    }
    override suspend fun getRecords():List<Record>{
        return localDatabase.getRecords()
    }
}