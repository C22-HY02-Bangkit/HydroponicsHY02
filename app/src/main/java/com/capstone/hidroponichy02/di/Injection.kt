package com.capstone.hidroponichy02.di

import android.content.Context
import com.capstone.hidroponichy02.di.api.ApiConfig
import com.example.storyapp.repository.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}