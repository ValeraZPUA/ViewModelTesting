package com.example.viewmodeltesting.network

import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory


object RetrofitService {
    private val BASE_URL = "https://randomuser.me/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(OkHttpClient()
            .newBuilder()
            .build())
        .build()

     fun getInterface(): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}