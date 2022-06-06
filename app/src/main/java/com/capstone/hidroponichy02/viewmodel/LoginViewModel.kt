package com.capstone.hidroponichy02.viewmodel

import androidx.lifecycle.ViewModel
import com.capstone.hidroponichy02.repository.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun login(email: String, pass: String) =
        storyRepository.login(email, pass)

}