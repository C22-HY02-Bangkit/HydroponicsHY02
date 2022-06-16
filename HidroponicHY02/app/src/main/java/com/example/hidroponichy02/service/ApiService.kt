package com.example.hidroponichy02.service

import com.example.hidroponichy02.response.LoginResponse
import com.example.hidroponichy02.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun signUp(
        @Field("fullname") name: String,
        @Field("email") email: String,
        @Field("password") pass: String
    ): UserResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun signIn(
        @Field("email") email: String,
        @Field("password") pass: String
    ): LoginResponse

}