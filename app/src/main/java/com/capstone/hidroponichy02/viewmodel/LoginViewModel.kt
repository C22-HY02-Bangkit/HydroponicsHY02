package com.capstone.hidroponichy02.viewmodel

import androidx.lifecycle.ViewModel
import com.capstone.hidroponichy02.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun login(email: String, pass: String) =
        userRepository.login(email, pass)

}