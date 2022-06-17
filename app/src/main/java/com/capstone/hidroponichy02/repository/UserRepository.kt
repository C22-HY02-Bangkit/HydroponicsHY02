package com.capstone.hidroponichy02.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.capstone.hidroponichy02.data.DevicePagingSource
import com.capstone.hidroponichy02.response.*
import com.capstone.hidroponichy02.service.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository(
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
                val response = apiService.register(name, email, pass)
                if (response.code == 200) {
                    emit(ResultResponse.Success(response))
                } else {
                    Log.e(TAG, "Register Fail")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Register Exception: ${e.message.toString()} ")
            }
        }


    fun login(email: String, pass: String): LiveData<ResultResponse<data>> =
        liveData {
            emit(ResultResponse.Loading)
            try {
                val response = apiService.login(email, pass)
                if (response.code == 200) {
                    emit(ResultResponse.Success(response.data))
                } else {
                    Log.e(TAG, "Register Fail ${response.status}")
                    emit(ResultResponse.Error(response.status))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Register Exception: ${e.message.toString()} ")
                emit(ResultResponse.Error(e.message.toString()))
            }
        }

    fun getUserDevice(token: String): Flow<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                DevicePagingSource(apiService, token)
            }
        ).flow
    }
    fun update(
        admin_id: String,
        status: Int
    ): LiveData<ResultResponse<DeviceResponse>> =
        liveData {
            emit(ResultResponse.Loading)
            try {
                val response = apiService.updatedevice(admin_id, status)
                if (response.code == 200) {
                    emit(ResultResponse.Success(response))
                } else {
                    Log.e(TAG, "Register Fail")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Register Exception: ${e.message.toString()} ")
            }
        }


    companion object {
        private const val TAG = "UserRepository"
    }
}