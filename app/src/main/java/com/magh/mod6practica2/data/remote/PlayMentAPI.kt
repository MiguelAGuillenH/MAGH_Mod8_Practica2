package com.magh.mod6practica2.data.remote

import com.magh.mod6practica2.data.remote.model.TrackDTO
import com.magh.mod6practica2.data.remote.model.TrackDetailDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayMentAPI {

    //https://private-839e32-playment1.apiary-mock.com/tracks
    @GET("tracks")
    fun getTracks(): Call<List<TrackDTO>> //Así se llamaría: getGames()

    //https://private-839e32-playment1.apiary-mock.com/tracks/track_detail/7EDmX
    @GET("tracks/track_detail/{id}")
    fun getTrackDetail(
        @Path("id") id: String?
    ): Call<TrackDetailDTO> //Así se llamaría: getGameDetail("7EDmX")

}