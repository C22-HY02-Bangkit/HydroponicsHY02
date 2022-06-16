package com.capstone.hidroponichy02.service

import com.capstone.hidroponichy02.response.DeviceResponse
import com.capstone.hidroponichy02.response.LoginResponse
import com.capstone.hidroponichy02.response.UserResponse
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("users/register")
    suspend fun register(
        @Field("fullname") name: String,
        @Field("email") email: String,
        @Field("password") pass: String
    ): UserResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") pass: String
    ): LoginResponse


    @GET("devices")
    suspend fun devices(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): DeviceResponse

}