package com.example.viewmodeltesting.network

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit




object RetrofitService {
    private val BASE_URL = "https://randomuser.me/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient()
            .newBuilder()
            .build())
        .build()

     fun getInterface(): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}