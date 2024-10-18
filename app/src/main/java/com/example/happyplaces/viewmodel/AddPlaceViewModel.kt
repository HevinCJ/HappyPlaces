package com.example.happyplaces.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happyplaces.models.Destination
import com.example.happyplaces.result.RetrofitResult
import com.example.happyplaces.services.DestinationService
import com.example.happyplaces.services.ServiceBuilder
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPlaceViewModel:ViewModel() {


    private val _newplace = MutableLiveData<RetrofitResult<Destination>>()
    val newplace: LiveData<RetrofitResult<Destination>> get() = _newplace

    fun PostNewPlacesToServer(destination: Destination){

        try {
            viewModelScope.launch {
                _newplace.value = RetrofitResult.Loading()

                val DestinationService = ServiceBuilder.buildService(DestinationService::class.java)
                val retrofitcall = DestinationService.PostDestination(destination)

                retrofitcall.enqueue(object :Callback<Destination>{
                    override fun onResponse(p0: Call<Destination>, p1: Response<Destination>) {
                        if (p1.isSuccessful){

                            val response = p1.body()
                            response?.let {
                                _newplace.value = RetrofitResult.Success(response)
                            }
                        }
                    }

                    override fun onFailure(p0: Call<Destination>, p1: Throwable) {
                        _newplace.value = RetrofitResult.Error(p1.message)
                    }

                })

            }



        }catch (e:Exception){

        }
    }


}