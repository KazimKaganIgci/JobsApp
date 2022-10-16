package com.kazim.jobsapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kazim.jobsapp.MainActivity
import com.kazim.jobsapp.R
import com.kazim.jobsapp.adapter.FavJobAdapter
import com.kazim.jobsapp.databinding.FragmentSavedBinding
import com.kazim.jobsapp.db.FavoriteJob
import com.kazim.jobsapp.viewmodel.JobViewModel

class SavedFragment : Fragment(R.layout.fragment_saved),FavJobAdapter.OnItemClickListener {
    private var _binding:FragmentSavedBinding ?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: JobViewModel
    private lateinit var favAdapter: FavJobAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            _binding =FragmentSavedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        favAdapter=FavJobAdapter(this)
        binding.rvJobsSaved.apply {
            layoutManager=LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object :DividerItemDecoration(activity,LinearLayout.VERTICAL){})
            adapter =favAdapter
        }
        viewModel.getALLFavJob().observe(viewLifecycleOwner){
            favAdapter.differ.submitList(it)
            updateUI(it)
        }


    }

    private fun updateUI(job: List<FavoriteJob>) {
        if (job.isNotEmpty()){
            binding.rvJobsSaved.visibility=View.VISIBLE
            binding.cardNoAvailable.visibility=View.GONE
        }
        else{
            binding.rvJobsSaved.visibility=View.GONE
            binding.cardNoAvailable.visibility=View.VISIBLE

        }

    }



    override fun onItemClick(job: FavoriteJob, view: View, position: Int) {
        deleteJob(job)

    }

    private fun deleteJob(job: FavoriteJob) {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Job")
            setMessage("Are you sure you want to delete job ?")
            setPositiveButton("DELETE"){_,_->
                viewModel.deleteFavJob(job)
                Toast.makeText(activity,"Job is deleted",Toast.LENGTH_LONG).show()
            }
            setNegativeButton("Cancel ",null,)

        }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}