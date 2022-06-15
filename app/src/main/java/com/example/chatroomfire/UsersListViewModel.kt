package com.example.chatroomfire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private var _user: MutableLiveData<Resource<ArrayList<UserDataClass>>> = MutableLiveData()
    val user: LiveData<Resource<ArrayList<UserDataClass>>> = _user

    init {
        userList()
    }

    fun userList() {
        _user.postValue(Resource.Loading())
        repository.getUserList { data, msg ->
            if (data.isNotEmpty()) {
                _user.postValue(Resource.Success(data))
            } else {
                _user.postValue(Resource.Error(msg))
            }

        }

    }
}