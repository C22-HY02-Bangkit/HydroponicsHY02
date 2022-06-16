package com.example.hidroponichy02.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val email: String,
    val password: String,
    val isLogin: Boolean
) : Parcelable