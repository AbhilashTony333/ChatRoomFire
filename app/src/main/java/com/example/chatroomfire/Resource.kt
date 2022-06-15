package com.example.chatroomfire

sealed class Resource<T>(
    var data: T? = null,
    var message: String? = null
) {

    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(msg: String?) : Resource<T>(message = msg)
    class Loading<T>() : Resource<T>()
}
