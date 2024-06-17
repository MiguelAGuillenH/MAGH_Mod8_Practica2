package com.magh.mod8practica2.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationDTO (

    @SerializedName("latitude")
    var latitude: Double? = null,

    @SerializedName("longitude")
    var longitude: Double? = null

): Parcelable