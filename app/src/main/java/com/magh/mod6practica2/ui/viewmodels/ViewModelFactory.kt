package com.magh.mod6practica2.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.magh.mod6practica2.data.PlayMentRepository

@Suppress("UNCHECKED_CAST")
class TracksListViewModelFactory(private val repository: PlayMentRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TracksListViewModel(repository) as T

}

@Suppress("UNCHECKED_CAST")
class TrackDetailViewModelFactory(private val repository: PlayMentRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TrackDetailViewModel(repository) as T

}


