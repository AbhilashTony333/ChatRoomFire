package com.example.chatroomfire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _result: MutableLiveData<String> = MutableLiveData()
    val result: LiveData<String> = _result

    fun signInAccount(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.signInUser(email, password)
            }.onSuccess {
                if (it.isSuccessful) {
                    _result.postValue("Success")
                } else _result.postValue(it.exception?.message.toString())
            }.onFailure {
                _result.postValue(it.message.toString())
            }

        }

    }
}