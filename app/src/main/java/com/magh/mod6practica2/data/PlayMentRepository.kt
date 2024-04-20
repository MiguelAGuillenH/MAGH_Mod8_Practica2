package com.magh.mod6practica2.data

import com.magh.mod6practica2.data.remote.PlayMentAPI
import com.magh.mod6practica2.data.remote.model.TrackDTO
import com.magh.mod6practica2.data.remote.model.TrackDetailDTO
import retrofit2.Call
import retrofit2.Retrofit

class PlayMentRepository(private val retrofit: Retrofit) {

    private val playmentAPI: PlayMentAPI = retrofit.create(PlayMentAPI::class.java)

    fun getTracks(): Call<List<TrackDTO>> {
        //delay((1000..4000).random().toLong())
        return playmentAPI.getTracks()
    }

    fun getTrackDetail(id: String?): Call<TrackDetailDTO> {
        //delay((1000..4000).random().toLong())
        return playmentAPI.getTrackDetail(id)
    }

}