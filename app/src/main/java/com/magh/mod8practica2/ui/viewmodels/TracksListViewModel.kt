package com.magh.mod8practica2.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magh.mod8practica2.R
import com.magh.mod8practica2.data.PlayMentRepository
import com.magh.mod8practica2.data.remote.model.TrackDTO
import com.magh.mod8practica2.utils.StringValue
import com.magh.mod8practica2.utils.StringValue.DynamicString
import com.magh.mod8practica2.utils.StringValue.StringResource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TracksListViewModel(private val repository: PlayMentRepository): ViewModel() {

    private val _tracks = MutableLiveData<List<TrackDTO>>()
    val tracks: LiveData<List<TrackDTO>> = _tracks

    private val _messageArray = MutableLiveData<List<StringValue>>()
    val messageArray: LiveData<List<StringValue>> = _messageArray

    fun getAllTracks(){
        viewModelScope.launch {

            val call = repository.getTracks()
            call.enqueue(object: Callback<List<TrackDTO>> {
                //Server response
                override fun onResponse(p0: Call<List<TrackDTO>>, response: Response<List<TrackDTO>>) {
                    response.body()?.let { responseTracks ->
                        _tracks.postValue(responseTracks)
                    }
                }
                //On error
                override fun onFailure(p0: Call<List<TrackDTO>>, e: Throwable) {
                    e.printStackTrace()
                    _messageArray.postValue(listOf(
                        StringResource(R.string.message_error_connection),
                        DynamicString(e.message.toString())
                    ))
                }
            })

        }
    }

}