package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.ProductDetailsResponse
import com.example.ecommerceapp.remote.ApiClient
import com.example.ecommerceapp.repository.ProductDetailsRepository
import com.example.ecommerceapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductDetailsViewModel : ViewModel() {
    private val repository = ProductDetailsRepository(apiService = RetrofitClient.instance)
    val productDetailsResponse = MutableLiveData<Response<ProductDetailsResponse>>()

    fun fetchProductDetails(productId: Int) {
        viewModelScope.launch {
            val response = repository.getProductDetails(productId)
            productDetailsResponse.postValue(response)
        }
    }
}