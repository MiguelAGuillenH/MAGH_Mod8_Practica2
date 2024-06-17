package com.magh.mod8practica2.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.magh.mod8practica2.data.remote.model.TrackDTO
import com.magh.mod8practica2.databinding.TrackItemBinding
import com.squareup.picasso.Picasso

class TrackViewHolder(private var binding: TrackItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(track: TrackDTO){
        binding.apply {
            lblTrack.text = track.name
            lblArtist.text = track.artist
            Picasso.get()
                .load(track.albumCover)
                .into(imgAlbumCover)
        }
    }

}