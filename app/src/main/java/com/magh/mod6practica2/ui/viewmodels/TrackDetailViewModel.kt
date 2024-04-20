package com.magh.mod6practica2.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magh.mod6practica2.R
import com.magh.mod6practica2.data.PlayMentRepository
import com.magh.mod6practica2.data.remote.model.TrackDetailDTO
import com.magh.mod6practica2.utils.StringValue
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackDetailViewModel(private val repository: PlayMentRepository): ViewModel()  {

    private val _track = MutableLiveData<TrackDetailDTO>()
    val tracks: LiveData<TrackDetailDTO> = _track

    private val _messageArray = MutableLiveData<List<StringValue>>()
    val messageArray: LiveData<List<StringValue>> = _messageArray

    fun getTrackDetail(trackId: String){
        viewModelScope.launch {

            val call = repository.getTrackDetail(trackId)
            call.enqueue(object: Callback<TrackDetailDTO> {
                //Server response
                override fun onResponse(p0: Call<TrackDetailDTO>, response: Response<TrackDetailDTO>) {
                    response.body()?.let { responseTrack ->
                        _track.postValue(responseTrack)
                    }
                }
                //On error
                override fun onFailure(p0: Call<TrackDetailDTO>, e: Throwable) {
                    e.printStackTrace()
                    _messageArray.postValue(listOf(
                        StringValue.StringResource(R.string.message_error_connection),
                        StringValue.DynamicString(e.message.toString())
                    ))
                }
            })

        }
    }

}