package com.example.imagesapp.ui.viewmodels
import androidx.lifecycle.ViewModel
import com.example.imagesapp.repositories.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnsplashViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository):
    ViewModel() {

}