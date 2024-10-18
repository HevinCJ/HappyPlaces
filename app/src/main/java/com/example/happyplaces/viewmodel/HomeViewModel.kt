package com.example.happyplaces.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happyplaces.models.Destination
import com.example.happyplaces.result.RetrofitResult
import com.example.happyplaces.services.DestinationService
import com.example.happyplaces.services.MessageService
import com.example.happyplaces.services.ServiceBuilder
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {

    private val _destinations = MutableLiveData<RetrofitResult<List<Destination>>>()
    val destinations:LiveData<RetrofitResult<List<Destination>>> get() = _destinations

    private val _message = MutableLiveData<RetrofitResult<String>>()
    val message:LiveData<RetrofitResult<String>> get() = _message


    fun LoadDestinationsFromServer(){

        try {
          viewModelScope.launch {
              _destinations.value = RetrofitResult.Loading()

              val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
              val requestCall = destinationService.getDestinationList()

              requestCall.enqueue(object :Callback<List<Destination>>{

                  override fun onResponse(
                      p0: Call<List<Destination>>,
                      p1: Response<List<Destination>>
                  ) {

                      if (p1.isSuccessful){
                          val destinations = p1.body()
                          destinations?.let {
                              _destinations.value = RetrofitResult.Success(it)
                          }

                      }

                  }

                  override fun onFailure(p0: Call<List<Destination>>, p1: Throwable) {
                      _destinations.value = RetrofitResult.Error(p1.message)
                  }

              })
          }

        }catch (e:Exception){

        }


    }


    fun LoadMessageFromServer(){
        try {
           viewModelScope.launch {
               _message.value = RetrofitResult.Loading()

               val messageService = ServiceBuilder.buildService(MessageService::class.java)
               val requestCall =   messageService.getMessages("http://10.0.2.2:7000/messages")

               requestCall.enqueue(object :Callback<String>{
                   override fun onResponse(p0: Call<String>, p1: Response<String>) {
                       if (p1.isSuccessful){
                           val message = p1.body()
                           message?.let {
                               _message.value = RetrofitResult.Success(it)
                           }
                       }
                   }

                   override fun onFailure(p0: Call<String>, p1: Throwable) {
                       _message.value = RetrofitResult.Error(p1.message)
                   }

               })
           }

        }catch (e:Exception){

        }
    }

}