package com.deto.mediaexplorer.data.remote.services

import com.deto.mediaexplorer.data.Category
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryService {

    @GET("api/categories")
    suspend fun getCategories(): List<Category>

    @POST("api/categories")
    suspend fun addCategory(@Body category: Category): Category

    @DELETE("api/categories/{id}")
    suspend fun deleteCategoryById(@Path("id") id: Int): Category

}