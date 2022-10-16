package com.kazim.jobsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kazim.jobsapp.db.FavoriteJob
import com.kazim.jobsapp.model.Job
import com.kazim.jobsapp.repository.JobRepository
import kotlinx.coroutines.launch
import retrofit2.http.Query

class JobViewModel(
    private val app: Application,
    private val jobRepository: JobRepository
):AndroidViewModel(app){

    fun jobResult()=jobRepository.jobResult()
    fun addFavJob(job:FavoriteJob)=viewModelScope.launch {
        jobRepository.addFavoriteJob(job)
    }
    fun deleteFavJob(job:FavoriteJob)=viewModelScope.launch {
        jobRepository.deleteFavoriteJob(job)
    }
    fun getALLFavJob()= jobRepository.getAllFavoriteJob()



    fun searchJob(search:String) =jobRepository.searchJob(search)
    fun searchResult()=jobRepository.searchJobResult()







}