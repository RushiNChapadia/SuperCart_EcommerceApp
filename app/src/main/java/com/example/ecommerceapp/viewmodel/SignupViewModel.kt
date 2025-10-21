package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.SignupRequest
import com.example.ecommerceapp.model.SignupResponse
import com.example.ecommerceapp.remote.ApiService
import com.example.ecommerceapp.repository.SignupRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class SignupViewModel : ViewModel() {

    private val repository = SignupRepository(ApiService.getInstance())

    private val _signupResponse = MutableLiveData<SignupResponse>()
    val signupResponse: LiveData<SignupResponse> = _signupResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun registerUser(signupRequest: SignupRequest) {
        viewModelScope.launch {
            try {
                val response: Response<SignupResponse> = repository.registerUser(signupRequest)
                if (response.isSuccessful && response.body() != null) {
                    _signupResponse.postValue(response.body())
                } else {
                    _errorMessage.postValue("Signup failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.localizedMessage}")
            }
        }
    }
}