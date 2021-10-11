package com.example.viewmodeltesting.ui

import androidx.lifecycle.*
import com.example.viewmodeltesting.model.RandomUserResponse
import com.example.viewmodeltesting.network.ApiInterface
import com.example.viewmodeltesting.network.RetrofitService
import com.example.viewmodeltesting.utils.MESSAGE
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityViewModel(state: SavedStateHandle) : ViewModel(), LifecycleObserver {

    private val savedStateHandle = state
    private var apiInterface: ApiInterface = RetrofitService.getInterface()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean>
        get() = _isFetching

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun saveCurrentMessage() {
        if (message.value != null) {
            savedStateHandle[MESSAGE] =  message.value
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getCurrentMessage() {
        if (savedStateHandle.contains(MESSAGE)) {
            updateTextView(savedStateHandle.get(MESSAGE) ?: "")
        }
    }

    fun updateTextView(newMessage: String) {
        _message.value = newMessage
    }

    fun uploadRandomUser() {
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