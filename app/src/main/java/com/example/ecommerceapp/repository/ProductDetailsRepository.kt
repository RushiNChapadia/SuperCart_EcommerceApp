package com.example.ecommerceapp.repository

import com.example.ecommerceapp.model.ProductDetailsResponse
import com.example.ecommerceapp.remote.ApiService
import retrofit2.Response

class ProductDetailsRepository(private val apiService: ApiService) {
    suspend fun getProductDetails(productId: Int): Response<ProductDetailsResponse> {
        return apiService.getProductDetails(productId)
    }
}