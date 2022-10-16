package com.kazim.jobsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kazim.jobsapp.repository.JobRepository

class JobViewModelFactory(val app:Application, private val jobRepository: JobRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return JobViewModel(app,jobRepository) as T
    }


}