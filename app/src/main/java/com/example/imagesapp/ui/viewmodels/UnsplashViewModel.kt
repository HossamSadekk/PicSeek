package com.example.imagesapp.ui.viewmodels
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.imagesapp.repositories.UnsplashRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnsplashViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository, savedStateHandle: SavedStateHandle):
    ViewModel() {
    companion object{
        private const val DEFAULT_SEARCH_VALUE = "programming"
        private const val CURRENT_QUERY = "currentQuery"
    }

    private val currentQuery = savedStateHandle.getLiveData(CURRENT_QUERY, DEFAULT_SEARCH_VALUE)

    val images = currentQuery.switchMap {
        unsplashRepository.getSearchResults(it).cachedIn(viewModelScope)
    }

    fun searchImage(query: String){
        currentQuery.value = query
    }
}