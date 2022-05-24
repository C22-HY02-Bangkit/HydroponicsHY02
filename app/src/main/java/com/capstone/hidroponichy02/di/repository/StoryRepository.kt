package com.example.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.hidroponichy02.di.api.ApiService
import com.example.storyapp.response.LoginResult
import com.example.storyapp.response.ResultResponse
import com.example.storyapp.response.UserResponse
class StoryRepository(
    private val apiService: ApiService,
) {

    fun signUp(name: String, email: String, pass: String): LiveData<ResultResponse<UserResponse>> =
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


    fun signIn(email: String, pass: String): LiveData<ResultResponse<LoginResult>> =
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