package com.example.ecommerceapp.model


import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String
)