package com.example.ecommerceapp.repository

import com.example.ecommerceapp.model.SignupRequest
import com.example.ecommerceapp.model.SignupResponse
import com.example.ecommerceapp.remote.ApiService
import retrofit2.Response

class SignupRepository(private val apiService: ApiService) {

    suspend fun registerUser(signupRequest: SignupRequest): Response<SignupResponse> {
        return apiService.signup(signupRequest)
    }
}