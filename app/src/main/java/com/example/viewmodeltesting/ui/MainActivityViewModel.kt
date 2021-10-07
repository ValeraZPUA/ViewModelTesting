package com.example.viewmodeltesting.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.viewmodeltesting.model.RandomUserResponse
import com.example.viewmodeltesting.network.ApiInterface
import com.example.viewmodeltesting.network.RetrofitService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(context: Application) : AndroidViewModel(context) {

    private val _message = MediatorLiveData<String>()
    private var apiInterface: ApiInterface = RetrofitService.getInterface()

    val message: LiveData<String>
        get() = _message

    fun updateTextView(newMessage: String) {
        _message.value = newMessage
    }

    fun uploadRandomUser() {
        /*apiInterface
            .getRandomUser()
            .enqueue(object : Callback<RandomUserResponse> {
                override fun onResponse(call: Call<RandomUserResponse>, response: Response<RandomUserResponse>) {
                    val userName = response.body()!!.results[0]["name"]?.get("first")
                    _message.value = userName
                }

                override fun onFailure(call: Call<RandomUserResponse>, t: Throwable) {
                    _message.value = "error"
                }
            })*/

        apiInterface
            .getRandomUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response -> _message.value = response.results[0]["name"]?.get("first")}
    }
}