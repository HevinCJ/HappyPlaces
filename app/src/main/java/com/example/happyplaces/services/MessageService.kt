package com.example.happyplaces.services

import com.example.happyplaces.models.Destination
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MessageService {

    @GET
    fun getMessages(@Url url:String):Call<String>
}