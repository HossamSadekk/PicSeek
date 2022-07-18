package com.example.imagesapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.imagesapp.R
import com.example.imagesapp.adapters.UnsplashImageAdapter
import com.example.imagesapp.adapters.UnsplashLoadStateAdapter
import com.example.imagesapp.databinding.FragmentGalleryBinding
import com.example.imagesapp.ui.viewmodels.UnsplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private var binding:FragmentGalleryBinding?= null
    private val viewModel by viewModels<UnsplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)
        val adapter = UnsplashImageAdapter()

        binding?.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashLoadStateAdapter{adapter.retry()},
                footer = UnsplashLoadStateAdapter{adapter.retry()},
            )
        }

        viewModel.images.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}