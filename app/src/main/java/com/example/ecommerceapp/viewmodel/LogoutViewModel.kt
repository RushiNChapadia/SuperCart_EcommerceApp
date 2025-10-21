package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.LogoutRequest
import com.example.ecommerceapp.model.LogoutResponse
import com.example.ecommerceapp.remote.ApiClient
import com.example.ecommerceapp.repository.LogoutRepository
import com.example.ecommerceapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class LogoutViewModel : ViewModel() {

    private val repository = LogoutRepository(apiService = RetrofitClient.instance)
    val logoutResponse = MutableLiveData<Response<LogoutResponse>>()

    fun logout(email: String) {
        viewModelScope.launch {
            val request = LogoutRequest(email)
            val response = repository.logoutUser(request)
            logoutResponse.postValue(response)
        }
    }
}