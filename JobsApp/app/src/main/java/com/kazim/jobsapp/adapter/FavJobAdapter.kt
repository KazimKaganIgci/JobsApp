package com.kazim.jobsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kazim.jobsapp.databinding.JobRowLayoutBinding
import com.kazim.jobsapp.db.FavoriteJob
import com.kazim.jobsapp.fragments.MainFragmentDirections
import com.kazim.jobsapp.model.Job

class FavJobAdapter constructor(
    private val itemClick:OnItemClickListener
) : RecyclerView.Adapter<FavJobAdapter.JobViewHolder>() {
    private var binding: JobRowLayoutBinding? = null

    class JobViewHolder(itemBinding: JobRowLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :
        DiffUtil.ItemCallback<FavoriteJob>() {
        override fun areItemsTheSame(oldItem: FavoriteJob, newItem: FavoriteJob): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteJob, newItem: FavoriteJob): Boolean {
            return newItem == oldItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        binding = JobRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JobViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentJob = differ.currentList[position]

        holder.itemView.apply {

            Glide.with(this)
                .load(currentJob.companyLogoUrl)
                .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType
            binding?.ibDelete?.visibility = View.VISIBLE

            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate?.text = dateJob?.get(0)




        }.setOnClickListener {
            val tags = arrayListOf<String>()
            val job = Job(currentJob.candidateRequiredLocation,currentJob.category,currentJob.company_logo,currentJob.companyLogoUrl
                ,currentJob.companyName,currentJob.description,currentJob.jobId,currentJob.jobType,currentJob.publicationDate,currentJob.salary
                ,tags,currentJob.title,currentJob.url)
            val direction = MainFragmentDirections.actionMainFragmentToDetailFragment(job)
            it.findNavController().navigate(direction)


        }
        holder.itemView.apply {
            binding?.ibDelete?.setOnClickListener {
                itemClick.onItemClick(currentJob,binding?.ibDelete!!,position)
            }
        }




    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener{
        fun onItemClick(job:FavoriteJob,view:View,position: Int)

    }


}