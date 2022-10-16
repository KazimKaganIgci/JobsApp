package com.kazim.jobsapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kazim.jobsapp.MainActivity
import com.kazim.jobsapp.R
import com.kazim.jobsapp.adapter.JobAdapter
import com.kazim.jobsapp.databinding.FragmentFeedBinding
import com.kazim.jobsapp.utils.Constants
import com.kazim.jobsapp.viewmodel.JobViewModel

class FeedFragment : Fragment(R.layout.fragment_feed),SwipeRefreshLayout.OnRefreshListener {
    private var _binding:FragmentFeedBinding ?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: JobViewModel
    private lateinit var jobAdapter: JobAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentFeedBinding.inflate(inflater,container,false)
        swipeRefreshLayout =binding.swipeContainer
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN,Color.RED,Color.BLUE,Color.DKGRAY)
        swipeRefreshLayout.post {
            swipeRefreshLayout.isRefreshing=true
            setupRecyclerView()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setupRecyclerView()



    }

    private fun setupRecyclerView() {
        jobAdapter = JobAdapter()
        binding.rvRemoteJobs.apply {
            layoutManager=LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object :DividerItemDecoration(activity,LinearLayout.VERTICAL){})
            adapter =jobAdapter
        }
        fetchingData()
    }

    private fun fetchingData() {
        if (Constants.CheckInternetConnection(requireContext())){
            viewModel.jobResult().observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    jobAdapter.differ.submitList(response.jobs)
                    swipeRefreshLayout.isRefreshing=false
                }
            }
        }else{
            Toast.makeText(activity, "No internet", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onRefresh() {
        setupRecyclerView()
    }

}