package com.kazim.jobsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kazim.jobsapp.databinding.ActivityMainBinding
import com.kazim.jobsapp.db.FavoriteJobDatabase
import com.kazim.jobsapp.repository.JobRepository
import com.kazim.jobsapp.viewmodel.JobViewModel
import com.kazim.jobsapp.viewmodel.JobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    lateinit var viewModel:JobViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title=""
        setupViewModel()
    }

    private fun setupViewModel() {
        val jobRepository=JobRepository(FavoriteJobDatabase(this))
        val jobViewModelFactory =JobViewModelFactory(application,jobRepository)
        viewModel= ViewModelProvider(this,jobViewModelFactory)[JobViewModel::class.java]

    }
}