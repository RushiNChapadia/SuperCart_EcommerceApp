package com.example.ecommerceapp.remote

import com.example.ecommerceapp.model.AddAddressRequest
import com.example.ecommerceapp.model.AddressListResponse
import com.example.ecommerceapp.model.ApiResponse
import com.example.ecommerceapp.model.CategoryResponse
import com.example.ecommerceapp.model.LoginRequest
import com.example.ecommerceapp.model.LoginResponse
import com.example.ecommerceapp.model.LogoutRequest
import com.example.ecommerceapp.model.LogoutResponse
import com.example.ecommerceapp.model.OrderDetailsResponse
import com.example.ecommerceapp.model.OrderRequest
import com.example.ecommerceapp.model.OrderResponse
import com.example.ecommerceapp.model.ProductDetailsResponse
import com.example.ecommerceapp.model.ProductResponse
import com.example.ecommerceapp.model.SignupRequest
import com.example.ecommerceapp.model.SignupResponse
import com.example.ecommerceapp.model.SubCategoryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("User/auth")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>


    @Headers("Content-Type: application/json")
    @POST("User/register")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<SignupResponse>

    @Headers("Content-Type: application/json")
    @GET("Category")
    fun getCategories(): Call<CategoryResponse>
    companion object{
        fun getInstance(): ApiService=ApiClient.retrofit.create(ApiService::class.java)
    }

    @GET("SubCategory")
    @Headers("Content-Type: application/json")
    suspend fun getSubCategories(
        @Query("category_id") categoryId: Int
    ): Response<SubCategoryResponse>

    @GET("SubCategory/products/{sub_category_id}")
    @Headers("Content-Type: application/json")
    suspend fun getProductsBySubCategory(
        @Path("sub_category_id") subCategoryId: Int
    ): Response<ProductResponse>

    @POST("User/logout")
    @Headers("Content-Type: application/json")
    suspend fun logoutUser(@Body logoutRequest: LogoutRequest): Response<LogoutResponse>

    @GET("Product/details/{product_id}")
    @Headers("Content-Type: application/json")
    suspend fun getProductDetails(
        @Path("product_id") productId: Int
    ): Response<ProductDetailsResponse>

    @POST("User/address")
    @Headers("Content-Type: application/json")
    suspend fun addAddress(@Body request: AddAddressRequest): Response<ApiResponse>

    @GET("User/addresses/{user_id}")
    @Headers("Content-Type: application/json")
    suspend fun getAddresses(@Path("user_id") userId: Int): Response<AddressListResponse>

    @Headers("Content-Type: application/json")
    @POST("Order")
    suspend fun placeOrder(@Body request: OrderRequest): Response<OrderResponse>

    @Headers("Content-Type: application/json")
    @GET("Order")
    suspend fun getOrderDetails(@Query("order_id") orderId: Int): Response<OrderDetailsResponse>
}
