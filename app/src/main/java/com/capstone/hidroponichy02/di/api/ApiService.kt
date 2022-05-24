package com.capstone.hidroponichy02.di.api

import com.example.storyapp.response.AllStoriesResponse
import com.example.storyapp.response.LoginResponse
import com.example.storyapp.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun signUp(
        @Field("fullname") fullname: String,
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