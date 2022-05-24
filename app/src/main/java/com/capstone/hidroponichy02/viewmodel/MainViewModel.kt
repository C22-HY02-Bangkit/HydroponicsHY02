package com.capstone.hidroponichy02.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.model.UserModel
import com.example.storyapp.model.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): Flow<UserModel> {
        return pref.getUser()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}