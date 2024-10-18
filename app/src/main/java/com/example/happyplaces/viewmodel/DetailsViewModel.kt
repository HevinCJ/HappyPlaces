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

class DetailsViewModel:ViewModel() {

    private var _details = MutableLiveData<RetrofitResult<Destination>>()
    val details: LiveData<RetrofitResult<Destination>> get() = _details

    private var _deleted = MutableLiveData<RetrofitResult<Unit>>()
    val deleted: LiveData<RetrofitResult<Unit>> get() = _deleted



    fun getDestinationDetailsById(id: Int) {
        viewModelScope.launch {
            try {


                _details.value = RetrofitResult.Loading()

                val destinationService = ServiceBuilder.buildService(DestinationService::class.java)

                val destinationByIdCall = destinationService.getDestinationById(id)

                destinationByIdCall.enqueue(object : Callback<Destination> {
                    override fun onResponse(p0: Call<Destination>, p1: Response<Destination>) {
                        if (p1.isSuccessful) {

                            val destination = p1.body()!!
                            _details.value = RetrofitResult.Success(destination)
                        } else {
                            _details.value = RetrofitResult.Error(p1.message())
                        }
                    }

                    override fun onFailure(p0: Call<Destination>, p1: Throwable) {
                        _details.value = RetrofitResult.Error(p1.message)
                    }

                })

            } catch (e: Exception) {

            }
        }


    }

    fun deleteItemFromDatabase(id: Int){
        viewModelScope.launch {
            try {
                _deleted.value = RetrofitResult.Loading()

                val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
                val deleteCall = destinationService.deleteDestination(id)

                deleteCall.enqueue(object :Callback<Unit>{
                    override fun onResponse(p0: Call<Unit>, p1: Response<Unit>) {
                        if (p1.isSuccessful) {
                            _deleted.value = RetrofitResult.Success(Unit)
                        }
                    }

                    override fun onFailure(p0: Call<Unit>, p1: Throwable) {
                        _deleted.value = RetrofitResult.Error(p1.message)
                    }

                })

            }catch (e:Exception){
                _deleted.value = RetrofitResult.Error(e.message)
            }
        }
    }


}