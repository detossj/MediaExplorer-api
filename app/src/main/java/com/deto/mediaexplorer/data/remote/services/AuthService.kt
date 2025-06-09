package com.deto.mediaexplorer.data.remote.services

import com.deto.mediaexplorer.model.LoginRequest
import com.deto.mediaexplorer.model.LoginResponse
import com.deto.mediaexplorer.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/profile")
    suspend fun getUser(): User

    @POST("logout")
    suspend fun logout()
}