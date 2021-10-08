package com.example.viewmodeltesting.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.viewmodeltesting.model.RandomUserResponse
import com.example.viewmodeltesting.network.ApiInterface
import com.example.viewmodeltesting.network.RetrofitService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivityViewModel(context: Application) : AndroidViewModel(context) {

    private var apiInterface: ApiInterface = RetrofitService.getInterface()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean>
        get() = _isFetching

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
            .doOnSubscribe { showProgressBar(true) }
            .doAfterTerminate { showProgressBar(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (this::showNewValue) { throwable -> showError(throwable) }
    }

    private fun showNewValue(response: RandomUserResponse) {
        _message.value = response.results[0]["name"]?.get("first")
    }

    private fun showError(throwable: Throwable) {
        _message.value = throwable.message ?: "error"
    }

    private fun showProgressBar(isShowing: Boolean) {
        _isFetching.postValue(isShowing)
    }
}