package com.example.ecommerceapp.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("mobile_no")
    val mobileNo: String,
    @SerializedName("email_id")
    val emailId: String
)