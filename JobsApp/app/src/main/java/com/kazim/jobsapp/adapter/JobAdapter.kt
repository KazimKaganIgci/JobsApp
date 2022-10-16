package com.kazim.jobsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kazim.jobsapp.R

import com.kazim.jobsapp.databinding.JobRowLayoutBinding
import com.kazim.jobsapp.fragments.MainFragmentDirections
import com.kazim.jobsapp.model.Job

class JobAdapter : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    class JobViewHolder(itemBinding: JobRowLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :
        DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {

        val binding = JobRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentJob = differ.currentList[position]
        val imageView =holder.itemView.findViewById<ImageView>(R.id.ivCompanyLogo)
        val companyNametv =holder.itemView.findViewById<TextView>(R.id.tv_company_name)
        val tvJobLocation =holder.itemView.findViewById<TextView>(R.id.tv_job_location)
        val tvJobTitle =holder.itemView.findViewById<TextView>(R.id.tv_job_title)
        val tvJobType =holder.itemView.findViewById<TextView>(R.id.tv_job_type)
        val tvDate =holder.itemView.findViewById<TextView>(R.id.tv_date)

        holder.itemView.apply {
            companyNametv.text =currentJob.company_name
            tvJobLocation.text=currentJob.candidate_required_location
            tvJobTitle.text =currentJob.title
            tvJobType.text =currentJob.job_type
            Glide.with(this).load(currentJob.company_logo_url).into(imageView)
            val datejob=currentJob.publication_date?.split("T")
            tvDate.text=datejob?.get(0)

        }.setOnClickListener {
            val direction =MainFragmentDirections.actionMainFragmentToDetailFragment(currentJob)
            it.findNavController().navigate(direction)

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}