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
class SignUpViewModel @Inject constructor(private val rep: Repository) : ViewModel() {

    private val _res: MutableLiveData<String> = MutableLiveData()
    val res: LiveData<String> = _res

    fun createAccount(name: String, email: String, password: String) =
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                rep.createUser(name, email, password)
            }.onSuccess {
                if (it.isSuccessful) {
                    _res.postValue("Success")
                } else _res.postValue(it.exception?.message.toString())
            }.onFailure {
                _res.postValue(it.message.toString())
            }

        }

}