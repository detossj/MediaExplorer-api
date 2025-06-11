package com.deto.mediaexplorer.data.remote.services


import com.deto.mediaexplorer.data.Category
import com.deto.mediaexplorer.data.Element
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ElementService {

    @GET("api/elements")
    suspend fun getElementsByCategory(@Query("category_id") categoryId: Int): List<Element>

    @GET("api/elements/{id}")
    suspend fun getElementById(@Path("id") id: Int): Element

    @GET("api/elements")
    suspend fun getElements(): List<Element>

    @POST("api/elements")
    suspend fun addElement(@Body category: Element): Element

    @DELETE("api/elements/{id}")
    suspend fun deleteElementById(@Path("id") id: Int): Category
}