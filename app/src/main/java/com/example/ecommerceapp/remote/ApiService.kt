package com.example.ecommerceapp.remote

import com.example.ecommerceapp.model.LoginRequest
import com.example.ecommerceapp.model.LoginResponse
import com.example.ecommerceapp.model.SignupRequest
import com.example.ecommerceapp.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("User/auth")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>


    @Headers("Content-Type: application/json")
    @POST("User/register")
    fun signup(@Body signupRequest: SignupRequest): Call<SignupResponse>

    companion object{
        fun getInstance(): ApiService=ApiClient.retrofit.create(ApiService::class.java)

    }
}