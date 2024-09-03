package com.example.stayhydratedapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity
data class Record(
    @PrimaryKey(autoGenerate = true)
     val  recordId: Int = 0,
     val recordDate: String= SimpleDateFormat("h:mm a MMM dd", Locale.getDefault()).format(Date()),
    val recordIntakeAmount: Int=0
)
