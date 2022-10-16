package com.kazim.jobsapp.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "jav_job")
data class FavoriteJob(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val candidateRequiredLocation: String,
    val category: String,
    val company_logo: String,
    val companyLogoUrl: String,
    val companyName: String,
    val description: String,
    val jobId: Int,
    val jobType: String,
    val publicationDate: String,
    val salary: String,
    val title: String,
    val url: String
)