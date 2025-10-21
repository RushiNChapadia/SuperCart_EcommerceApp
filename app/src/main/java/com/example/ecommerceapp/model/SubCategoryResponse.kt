package com.example.ecommerceapp.model

data class SubCategoryResponse(
    val status: Int,
    val message: String,
    val subcategories: List<SubCategory>
)

data class SubCategory(
    val subcategory_id: String,
    val subcategory_name: String,
    val category_id: String,
    val subcategory_image_url: String,
    val is_active: String
)