package com.example.chatroomfire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private var _actDetails: MutableLiveData<Resource<UserDataClass>> = MutableLiveData()
    var actDetails: LiveData<Resource<UserDataClass>> = _actDetails

    fun accountUid(uid: String) {

        _actDetails.postValue(Resource.Loading())

        kotlin.runCatching {

            repository.getDetails(uid) { detail, mesg ->

                if (detail)

            }
        }
    }
}