package com.example.ecommerceapp.model

data class OrderDetailsResponse(
    val status: Int,
    val message: String,
    val order: OrderDetail?
)

data class OrderDetail(
    val order_id: String,
    val address_title: String,
    val address: String,
    val bill_amount: String,
    val payment_method: String,
    val order_status: String,
    val order_date: String,
    val items: List<OrderItemDetail>
)

data class OrderItemDetail(
    val product_id: String,
    val quantity: String,
    val unit_price: String,
    val amount: String,
    val product_name: String,
    val description: String,
    val product_image_url: String
)
