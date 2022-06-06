package com.capstone.hidroponichy02.viewmodel

import androidx.lifecycle.ViewModel
import com.capstone.hidroponichy02.repository.StoryRepository

class RegisterViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun register(name: String, email: String, pass: String) =
        storyRepository.register(name, email, pass)

}