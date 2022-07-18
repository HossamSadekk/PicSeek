package com.example.imagesapp.ui.viewmodels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.imagesapp.repositories.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnsplashViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository):
    ViewModel() {
    companion object{
        private const val DEFAULT_SEARCH_VALUE = "programming"
    }

    private val currentQuery = MutableLiveData(DEFAULT_SEARCH_VALUE)

    val images = currentQuery.switchMap {
        unsplashRepository.getSearchResults(it).cachedIn(viewModelScope)
    }

    fun searchImage(query: String){
        currentQuery.value = query
    }
}