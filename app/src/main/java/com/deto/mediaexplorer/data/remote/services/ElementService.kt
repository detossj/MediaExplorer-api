package com.deto.mediaexplorer.data.remote.services


import com.deto.mediaexplorer.data.Element
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ElementService {

    @GET("api/elements")
    suspend fun getElements(): List<Element>

    @POST("api/elements")
    suspend fun addElement(@Body category: Element): Element
}