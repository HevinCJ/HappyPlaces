package com.example.happyplaces.services

import com.example.happyplaces.models.Destination
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface DestinationService {

    @GET("destination")
    fun getDestinationList():Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestinationById(@Path("id") id:Int):Call<Destination>

    @POST("destination")
    fun PostDestination(@Body newdestination:Destination):Call<Destination>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(@Path("id") id: Int,
                          @Field("city") city:String?,
                          @Field("country") country:String?,
                          @Field("description") description:String?)
    :Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int):Call<Unit>

}