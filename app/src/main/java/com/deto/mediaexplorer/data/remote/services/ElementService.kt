package com.deto.mediaexplorer.data.remote.services


import com.deto.mediaexplorer.data.Element
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ElementService {

    @GET("api/elements")
    suspend fun getElementsByCategory(@Query("category_id") categoryId: Int): List<Element>


    @POST("api/elements")
    suspend fun addElement(@Body category: Element): Element
}