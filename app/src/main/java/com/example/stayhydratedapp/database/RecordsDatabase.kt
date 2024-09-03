package com.example.stayhydratedapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Record::class], version=3)
abstract class RecordsDatabase():RoomDatabase(){
    abstract fun recordDAO() : RecordDAO
    companion object{
        @Volatile
        private var INSTANCE:RecordsDatabase?= null

        fun getInstance(context: Context):RecordsDatabase{
            return INSTANCE?: synchronized(this){
                INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                   RecordsDatabase::class.java,
                    "RecordsDatabase"
                )   .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE= it
                    }
            }
        }
    }
}