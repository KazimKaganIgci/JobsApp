package com.kazim.jobsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteJobDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job:FavoriteJob): Long

    @Query("SELECT * FROM jav_job ORDER BY id DESC")
    fun getAllJob(): LiveData<List<FavoriteJob>>

    @Delete
    suspend fun deleteJob(job: FavoriteJob)
}