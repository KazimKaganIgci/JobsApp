package com.kazim.jobsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kazim.jobsapp.MainActivity
import com.kazim.jobsapp.R
import com.kazim.jobsapp.adapter.JobAdapter
import com.kazim.jobsapp.databinding.FragmentSearchBinding
import com.kazim.jobsapp.utils.Constants

import com.kazim.jobsapp.viewmodel.JobViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding:FragmentSearchBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: JobViewModel
    private lateinit var jobAdapter: JobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

        if (Constants.CheckInternetConnection(requireContext())){
            searchJob()
        }else{
            Toast.makeText(activity, "No internet", Toast.LENGTH_SHORT).show()

        }
    }

    private fun searchJob() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchJob(editable.toString())
                    }
                }
            }
        }
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        jobAdapter = JobAdapter()
        binding.rvSearchJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = jobAdapter
        }

        viewModel.searchResult().observe(viewLifecycleOwner) { remoteJob ->
            jobAdapter.differ.submitList(remoteJob.jobs)
        }

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
}