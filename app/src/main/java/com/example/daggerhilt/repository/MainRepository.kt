package com.example.daggerhilt.repository

import com.example.daggerhilt.model.BlogView
import com.example.daggerhilt.retrofit.BlogRetrofit
import com.example.daggerhilt.retrofit.NetworkMapper
import com.example.daggerhilt.room.BlogDao
import com.example.daggerhilt.room.CacheMapper
import com.example.daggerhilt.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val blogDao: BlogDao,
    private val retrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {

    suspend fun getBlog(): kotlinx.coroutines.flow.Flow<DataState<List<BlogView>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {

            val networkBlogs = retrofit.getData()
            val viewBlogs = networkMapper.toViewList(networkBlogs)
            viewBlogs.forEach { blog ->
                blogDao.insertBlog(cacheMapper.toEntity(blog))
            }

            val cachedBlogs = blogDao.getAllBlogs()
            emit(DataState.Success(cacheMapper.toViewList(cachedBlogs)))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}