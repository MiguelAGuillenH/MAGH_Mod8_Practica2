package com.magh.mod8practica2.data.remote.model

import com.google.gson.annotations.SerializedName

data class TrackDetailDTO (

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("artist")
    var artist: String? = null,

    @SerializedName("album")
    var album: String? = null,

    @SerializedName("album_cover")
    var albumCover: String? = null,

    @SerializedName("release_date")
    var releaseDate: String? = null,

    @SerializedName("disc_number")
    var discNumber: Int? = null,

    @SerializedName("track_number")
    var trackNumber: Int? = null,

    @SerializedName("duration_ms")
    var duration: Int? = null,

    @SerializedName("preview")
    var preview: String? = null,

    @SerializedName("video_url")
    var videoUrl: String? = null,

    @SerializedName("location")
    var location: LocationDTO? = null

)