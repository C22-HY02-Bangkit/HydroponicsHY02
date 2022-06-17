package com.capstone.hidroponichy02.service

import com.capstone.hidroponichy02.response.DeviceResponse
import com.capstone.hidroponichy02.response.LoginResponse
import com.capstone.hidroponichy02.response.UserResponse
import retrofit2.Call
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

    @FormUrlEncoded
    @PUT("devices/{device_id}")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImQ5NjI2ZmM1LTY5NTQtNGZmMC1hNGQ3LTQzMTRjYjg1MThkYyIsImlhdCI6MTY1NTE5OTM5MywiZXhwIjoxNjU3NzkxMzkzfQ.g3nNEY28yiFka-80f6rul-6UvADLsnzrv5yCBHQlTZk")
    suspend fun updatedevice(
        @Path("device_id") device_id:String,
        @Field("status") status: Int
    ): DeviceResponse
}