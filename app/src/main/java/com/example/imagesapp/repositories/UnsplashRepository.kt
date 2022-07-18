package com.example.imagesapp.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.imagesapp.api.UnsplashApi
import com.example.imagesapp.paging.UnsplashPagingSource
import javax.inject.Inject

class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResults(query:String) =
        Pager(
            config = PagingConfig(
                pageSize = 20, // how many items per request, this value that will be passed into "params.loadSize" when we make our request.
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {UnsplashPagingSource(unsplashApi,query)}
        ).liveData
}