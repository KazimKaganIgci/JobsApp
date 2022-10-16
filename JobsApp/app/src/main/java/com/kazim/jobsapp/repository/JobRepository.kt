package com.kazim.jobsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kazim.jobsapp.api.RetrofitInstance
import com.kazim.jobsapp.db.FavoriteJob
import com.kazim.jobsapp.db.FavoriteJobDatabase
import com.kazim.jobsapp.model.Job
import com.kazim.jobsapp.model.JobResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class JobRepository(private val db:FavoriteJobDatabase){

    private val jobService=RetrofitInstance.apiService
    private val jobResponseLiveData:MutableLiveData<JobResponse> = MutableLiveData()
    private val searchjobResponseLiveData:MutableLiveData<JobResponse> = MutableLiveData()


    init {

        getJobResponse()
    }

    private fun getJobResponse() {

            jobService.getJobResponse().enqueue(
            object : Callback<JobResponse> {
                override fun onResponse(call: Call<JobResponse>, response: Response<JobResponse>) {
                    if (response.body() != null) {
                        jobResponseLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<JobResponse>, t: Throwable) {
                    //jobResponseLiveData.postValue(null)
                    Log.d("error api", t.message.toString())
                }
            })
    }

    fun searchJob(query: String){
        jobService.searchJob(query).enqueue(object :Callback<JobResponse>{
            override fun onResponse(call: Call<JobResponse>, response: Response<JobResponse>) {
                if (response.body()!=null){
                    searchjobResponseLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<JobResponse>, t: Throwable) {
                Log.d("error api", t.message.toString())

            }
        })

    }





    fun jobResult(): LiveData<JobResponse> {
        return jobResponseLiveData
    }

    fun searchJobResult(): LiveData<JobResponse> {
        return searchjobResponseLiveData
    }

    suspend fun addFavoriteJob(job: FavoriteJob)=db.getRemoteJobDao().insertJob(job)
    suspend fun deleteFavoriteJob(job: FavoriteJob)=db.getRemoteJobDao().deleteJob(job)
    fun getAllFavoriteJob()=db.getRemoteJobDao().getAllJob()


}