package com.example.ecommerceapp.model

import com.google.gson.annotations.SerializedName

data class LogoutRequest(
    @SerializedName("email_id")
    val emailId: String
)