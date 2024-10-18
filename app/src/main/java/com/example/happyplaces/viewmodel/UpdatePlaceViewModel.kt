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

class UpdatePlaceViewModel:ViewModel() {

    private var _updates = MutableLiveData<RetrofitResult<Destination>>()
    val updates: LiveData<RetrofitResult<Destination>> get() = _updates


    fun updateDataInServer(destination: Destination) {
        viewModelScope.launch {

            try {
                _updates.value = RetrofitResult.Loading()

                val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
                val putRequest = destinationService.updateDestination(
                    destination.id,
                    destination.city,
                    destination.country,
                    destination.description
                )

                putRequest.enqueue(object : Callback<Destination> {
                    override fun onResponse(p0: Call<Destination>, p1: Response<Destination>) {
                        if (p1.isSuccessful) {
                            val response = p1.body()
                            response?.let {
                                _updates.value = RetrofitResult.Success(it)
                            }

                        }
                    }

                    override fun onFailure(p0: Call<Destination>, p1: Throwable) {
                        _updates.value = RetrofitResult.Error(p1.message)
                    }

                })


            }catch (e:Exception) {
                _updates.value = RetrofitResult.Error(e.message)
            }
        }

    }
}