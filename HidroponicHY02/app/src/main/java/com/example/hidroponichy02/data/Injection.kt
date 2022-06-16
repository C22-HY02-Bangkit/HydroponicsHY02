package com.example.hidroponichy02.data

import android.content.Context
import com.example.hidroponichy02.repository.StoryRepository
import com.example.hidroponichy02.service.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}