package com.magh.mod6practica2.data.remote.model

import com.google.gson.annotations.SerializedName

data class TrackDTO(

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("artist")
    var artist: String? = null,

    @SerializedName("album_cover")
    var albumCover: String? = null

)
