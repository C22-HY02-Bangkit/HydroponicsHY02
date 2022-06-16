package com.capstone.hidroponichy02.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val token: String,
    val verify_user: Boolean,
    val userid: String,
    val email: String,
    val fullname: String,
    val password: String,
    val isLogin: Boolean
) : Parcelable