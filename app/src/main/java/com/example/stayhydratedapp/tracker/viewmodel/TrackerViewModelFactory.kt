package com.example.stayhydratedapp.tracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stayhydratedapp.tracker.repo.TrackerRepo

class TrackerViewModelFactory(val trackerRepo: TrackerRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TrackerViewModel::class.java)) {
            TrackerViewModel(trackerRepo) as T
        }else{
            throw IllegalArgumentException("TrackerViewModel class not found")
        }
    }
}