package com.example.happyplaces.result


sealed class RetrofitResult<T>(val data:T?=null,val message:String?=null) {
    class Loading<T>():RetrofitResult<T>()
    class Success<T>(data: T):RetrofitResult<T>(data)
    class Error<T>(message: String?):RetrofitResult<T>(data = null,message)

}