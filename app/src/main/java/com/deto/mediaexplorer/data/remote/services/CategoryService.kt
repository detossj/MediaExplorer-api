package com.deto.mediaexplorer.data.remote.services

import com.deto.mediaexplorer.data.Category
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryService {

    @GET("api/categories")
    suspend fun getCategories(): List<Category>

    @POST("api/categories")
    suspend fun addCategory(@Body category: Category): Category
}