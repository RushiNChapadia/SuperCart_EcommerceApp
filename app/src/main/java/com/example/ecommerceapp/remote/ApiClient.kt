package com.example.ecommerceapp.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY

        }


        val client = OkHttpClient.Builder()
            .readTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        //192.168.1.241
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2/myshop/index.php/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

}