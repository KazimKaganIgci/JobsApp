package com.kazim.jobsapp.api

import com.kazim.jobsapp.model.JobResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JobAPI {

    @GET("remote-jobs?limit=50")
    fun getJobResponse():Call<JobResponse>

    @GET("remote-jobs")
    fun searchJob(@Query("search") search:String):Call<JobResponse>
}