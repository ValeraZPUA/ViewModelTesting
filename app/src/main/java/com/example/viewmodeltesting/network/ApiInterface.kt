package com.example.viewmodeltesting.network

import com.example.viewmodeltesting.model.RandomUserResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/?inc=name")
    //fun getRandomUser(): Call<RandomUserResponse>
    fun getRandomUser(): Observable<RandomUserResponse>
}