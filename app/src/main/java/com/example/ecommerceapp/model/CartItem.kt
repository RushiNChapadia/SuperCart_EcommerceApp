package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: String,
    val productName: String,
    val description: String,
    val product_image_url: String,
    val price: Double,
    val quantity: Int
)
