package com.example.imagesapp.paging

import androidx.paging.PagingSource
import com.example.imagesapp.api.UnsplashApi
import com.example.imagesapp.models.UnsplashImage
import retrofit2.HttpException
import java.io.IOException

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String // we pass the query because we know the query only on the run time.
) : PagingSource<Int, UnsplashImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        /** the position indicate to the number of page that we are need,but at the first time it will be null **/
        val position = params.key ?: 1

        return try {
            val response = unsplashApi.searchImage(query, position, params.loadSize) // params.loadSize it will be passed from PagerConfig constructor.
            val images = response.results

            LoadResult.Page(
                data = images,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (images.isEmpty()) null else position + 1
            ) // if everything is okay it will return one page of results in our recycler view.

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}