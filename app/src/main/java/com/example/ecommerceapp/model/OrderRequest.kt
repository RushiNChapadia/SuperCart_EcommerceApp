package com.example.ecommerceapp.model

data class OrderRequest(
    val user_id: Int,
    val delivery_address: DeliveryAddress,
    val items: List<OrderItem>,
    val bill_amount: Double,
    val payment_method: String
)

data class DeliveryAddress(
    val title: String,
    val address: String
)

data class OrderItem(
    val product_id: Int,
    val quantity: Int,
    val unit_price: Double
)