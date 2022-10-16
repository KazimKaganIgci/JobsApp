package com.kazim.jobsapp.model

data class JobResponse(
    val `0-legal-notice`: String,
    val `00-warning`: String,
    val `job-count`: Int,
    val jobs: List<Job>
)