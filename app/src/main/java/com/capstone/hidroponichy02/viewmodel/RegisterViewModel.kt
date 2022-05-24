package com.capstone.hidroponichy02.viewmodel


import androidx.lifecycle.ViewModel
import com.example.storyapp.repository.StoryRepository


class RegisterViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun signUp(fullname: String, email: String, pass: String) =
        storyRepository.signUp(fullname, email, pass)

}