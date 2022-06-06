package com.capstone.hidroponichy02.data

import android.content.Context
import com.capstone.hidroponichy02.repository.StoryRepository
import com.capstone.hidroponichy02.service.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}