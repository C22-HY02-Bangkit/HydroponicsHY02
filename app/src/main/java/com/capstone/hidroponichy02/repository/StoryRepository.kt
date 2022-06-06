package com.capstone.hidroponichy02.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.hidroponichy02.response.LoginResult
import com.capstone.hidroponichy02.response.ResultResponse
import com.capstone.hidroponichy02.response.UserResponse
import com.capstone.hidroponichy02.service.ApiService

class StoryRepository(
    private val apiService: ApiService,
) {

    fun register(
        name: String,
        email: String,
        pass: String
    ): LiveData<ResultResponse<UserResponse>> =
        liveData {
            emit(ResultResponse.Loading)
            try {
                val response = apiService.signUp(name, email, pass)
                if (!response.error) {
                    emit(ResultResponse.Success(response))
                } else {
                    Log.e(TAG, "Register Fail: ${response.message}")
                    emit(ResultResponse.Error(response.message))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Register Exception: ${e.message.toString()} ")
                emit(ResultResponse.Error(e.message.toString()))
            }
        }


    fun login(email: String, pass: String): LiveData<ResultResponse<LoginResult>> =
        liveData {
            emit(ResultResponse.Loading)
            try {
                val response = apiService.signIn(email, pass)
                if (!response.error) {
                    emit(ResultResponse.Success(response.loginResult))
                } else {
                    Log.e(TAG, "Register Fail: ${response.message}")
                    emit(ResultResponse.Error(response.message))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Register Exception: ${e.message.toString()} ")
                emit(ResultResponse.Error(e.message.toString()))
            }
        }

    companion object {
        private const val TAG = "StoryRepository"
    }
}