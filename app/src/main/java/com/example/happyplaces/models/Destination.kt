package com.example.happyplaces.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    val id:Int =0,
    val city:String? = null,
    val description:String? = null,
    val country:String? = null,
):Parcelable
