package com.example.ecommerceapp.repository

import com.example.ecommerceapp.model.LogoutRequest
import com.example.ecommerceapp.model.LogoutResponse
import com.example.ecommerceapp.remote.ApiService
import retrofit2.Response

class LogoutRepository(private val apiService: ApiService) {
    suspend fun logoutUser(logoutRequest: LogoutRequest): Response<LogoutResponse> {
        return apiService.logoutUser(logoutRequest)
    }
}