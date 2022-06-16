package com.example.hidroponichy02.helper


import android.util.Patterns


object Helper {

    fun isEmailValid(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}
