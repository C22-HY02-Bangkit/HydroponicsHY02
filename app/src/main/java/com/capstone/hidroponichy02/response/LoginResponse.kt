package com.capstone.hidroponichy02.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val data: data,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("status")
    val status: String
)

data class data(

    @field:SerializedName("token")
    val token: String,
    @field:SerializedName("verify_user")
    val verifyUser: Boolean,
    @field:SerializedName("userid")
    val userId: String,
    @field:SerializedName("fullname")
    val fullname: String,
    @field:SerializedName("email")
    val email: String
)
