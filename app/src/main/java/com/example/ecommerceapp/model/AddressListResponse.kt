package com.example.ecommerceapp.model

data class AddressListResponse(
    val status: Int,
    val message: String,
    val addresses: List<AddressModel>

)
