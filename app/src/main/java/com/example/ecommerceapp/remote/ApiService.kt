package com.example.ecommerceapp.remote

import com.example.ecommerceapp.model.CategoryResponse
import com.example.ecommerceapp.model.LoginRequest
import com.example.ecommerceapp.model.LoginResponse
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
}