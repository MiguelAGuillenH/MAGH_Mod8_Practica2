package com.magh.mod8practica2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magh.mod8practica2.data.remote.model.TrackDTO
import com.magh.mod8practica2.databinding.TrackItemBinding

class TrackAdapter: RecyclerView.Adapter<TrackViewHolder>() {

    private var tracks: List<TrackDTO> = emptyList()

    var onTrackClicked: ((TrackDTO) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]

        holder.bind(track)

        holder.itemView.setOnClickListener{
            onTrackClicked?.invoke(track)
        }
    }

    fun updateList(list: List<TrackDTO>){
        tracks = list
        notifyDataSetChanged()
    }

}