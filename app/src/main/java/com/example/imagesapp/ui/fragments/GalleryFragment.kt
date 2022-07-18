package com.example.imagesapp.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
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

        /** Setup the recycler view **/
        val adapter = UnsplashImageAdapter()
        binding?.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashLoadStateAdapter{adapter.retry()},
                footer = UnsplashLoadStateAdapter{adapter.retry()},
            )
        }

        /** Observing the live data of UnsplashPhotos **/
        viewModel.images.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

        setHasOptionsMenu(true) // this line is necessary so the search icon can appear.
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.unsplash_gallery_fragment_menu,menu)
        val search_item = menu.findItem(R.id.search)
        val searchView = search_item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    binding?.recyclerview?.scrollToPosition(0)
                    viewModel.searchImage(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}