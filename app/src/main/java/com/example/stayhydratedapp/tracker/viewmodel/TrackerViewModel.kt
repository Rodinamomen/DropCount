package com.example.stayhydratedapp.tracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stayhydratedapp.database.LocalDatabase
import com.example.stayhydratedapp.database.Record
import com.example.stayhydratedapp.tracker.repo.TrackerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackerViewModel(val trackerRepo: TrackerRepo): ViewModel() {
    private val _recordInserted = MutableLiveData<Boolean>()
    val recordInserted:LiveData<Boolean> = _recordInserted

    private val _records = MutableLiveData<List<Record>>()
    val records :LiveData<List<Record>> = _records
    fun insertRecord(record: Record){
        viewModelScope.launch(Dispatchers.IO) {
            trackerRepo.insertRecord(record)
            _recordInserted.postValue(true)

        }
    }
    fun getRecords(){
        viewModelScope.launch(Dispatchers.IO) {
            _records.postValue(trackerRepo.getRecords())
        }
    }

}