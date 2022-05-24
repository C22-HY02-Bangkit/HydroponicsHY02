package com.capstone.hidroponichy02.viewmodel


import androidx.lifecycle.ViewModel
import com.example.storyapp.repository.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun signIn(email: String, pass: String) =
        storyRepository.signIn(email, pass)

}