package com.capstone.hidroponichy02.data

import android.content.Context
import com.capstone.hidroponichy02.repository.UserRepository
import com.capstone.hidroponichy02.service.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService)
    }
}