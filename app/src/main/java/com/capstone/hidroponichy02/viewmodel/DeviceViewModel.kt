package com.capstone.hidroponichy02.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.hidroponichy02.repository.UserRepository
import com.capstone.hidroponichy02.response.DataItem

class DeviceViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUserDevice(token: String): LiveData<PagingData<DataItem>> {
        return userRepository.getUserDevice(token).cachedIn(viewModelScope).asLiveData()
    }

}