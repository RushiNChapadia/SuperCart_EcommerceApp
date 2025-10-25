package com.example.ecommerceapp.model

data class ProductDetailsResponse(
    val status: Int,
    val message: String,
    val product: ProductDetails
)

data class ProductDetails(
    val product_id: String,
    val product_name: String,
    val description: String,
    val category_id: String,
    val sub_category_id: String,
    val price: String,
    val average_rating: String,
    val product_image_url: String,
    val is_active: String,
    val images: List<ProductImage>,
    val specifications: List<Specification>,
    val reviews: List<Review>
)

data class ProductImage(
    val image: String,
    val display_order: String
)

data class Specification(
    val specification_id: String,
    val title: String,
    val specification: String,
    val display_order: String
)

data class Review(
    val user_id: String,
    val full_name: String,
    val review_id: String,
    val review_title: String,
    val review: String,
    val rating: String,
    val review_date: String
)
