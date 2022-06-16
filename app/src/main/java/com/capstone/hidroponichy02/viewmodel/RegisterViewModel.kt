package com.capstone.hidroponichy02.viewmodel

import androidx.lifecycle.ViewModel
import com.capstone.hidroponichy02.repository.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(name: String, email: String, pass: String) =
        userRepository.register(name, email, pass)

}