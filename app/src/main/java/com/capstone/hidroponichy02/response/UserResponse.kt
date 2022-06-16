package com.capstone.hidroponichy02.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("status")
    val status: String
)