package com.kazim.jobsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kazim.jobsapp.MainActivity
import com.kazim.jobsapp.R
import com.kazim.jobsapp.databinding.FragmentDetailBinding
import com.kazim.jobsapp.db.FavoriteJob
import com.kazim.jobsapp.model.Job
import com.kazim.jobsapp.viewmodel.JobViewModel


class DetailFragment : Fragment() {

    private var _binding:FragmentDetailBinding ?=null
    private val binding get() = _binding!!
    private lateinit var currentJob:Job
    private lateinit var viewModel: JobViewModel
    private val args:DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentDetailBinding.inflate(inflater,container,false)
        viewModel=(activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentJob =args.job
        setupWeb()
        binding.fabAddFavorite.setOnClickListener {
            addFavJob(view)
        }

    }

    private fun addFavJob(view: View) {
        val favJob =FavoriteJob(
              0,
              currentJob.candidate_required_location,
              currentJob.category,
              currentJob.company_logo,
              currentJob.company_logo_url,
              currentJob.company_name,
              currentJob.description,
              currentJob.id,
              currentJob.job_type,
              currentJob.publication_date,
              currentJob.salary,
              currentJob.title,
              currentJob.url,
        )
        viewModel.addFavJob(favJob)
        Snackbar.make(view,"Job Saved",Snackbar.LENGTH_LONG).show()


    }

    private fun setupWeb() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(currentJob.url)
        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            blockNetworkImage = false
            loadsImagesAutomatically = true
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}
