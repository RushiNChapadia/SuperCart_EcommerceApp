package com.example.ecommerceapp.model

data class OrderResponse(
    val status: Int,
    val message: String,
    val order_id: Int?
)