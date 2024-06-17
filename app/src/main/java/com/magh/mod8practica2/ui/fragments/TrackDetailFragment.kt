package com.magh.mod8practica2.ui.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.magh.mod8practica2.R
import com.magh.mod8practica2.application.PlayMentApp
import com.magh.mod8practica2.databinding.FragmentTrackDetailBinding
import com.magh.mod8practica2.ui.viewmodels.TrackDetailViewModel
import com.magh.mod8practica2.ui.viewmodels.TrackDetailViewModelFactory
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import java.util.Locale

private const val TRACK_ID = "track_id"

class TrackDetailFragment : Fragment() {

    private var _binding: FragmentTrackDetailBinding? = null
    private val binding get() = _binding!!

    //Variables for MVVM
    private val trackDetailViewModel: TrackDetailViewModel by activityViewModels {
        TrackDetailViewModelFactory(
            (activity?.application as PlayMentApp).repository //Repository
        )
    }

    //Parameters
    private var trackId: String? = null

    //Flag to check if it's a new fragment
    private var newFragment = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            trackId = it.getString(TRACK_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTrackDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Tracking tracks changes to update UI
        trackDetailViewModel.tracks.observe(viewLifecycleOwner, Observer {track ->
            binding.pbLoading.visibility = View.GONE

            binding.apply {
                txtTrackName.text = track.name
                txtTrackArtist.text = track.artist
                txtTrackAlbum.text = track.album

                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = formatter.parse(track.releaseDate)
                //val formatter2 = android.text.format.DateFormat.getMediumDateFormat(requireContext())
                val formatter2 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateString = formatter2.format(date)
                txtTrackRelease.text = getString(
                    R.string.format_key_value,
                    getString(R.string.label_release),
                    dateString
                )

                txtTrackDiscAndTrackNumber.text = getString(R.string.label_disc_and_track_number, track.discNumber, track.trackNumber)
                txtTrackDuration.text = getString(
                    R.string.format_key_value,
                    getString(R.string.label_duration),
                    DateUtils.formatElapsedTime(track.duration!!.toLong() / 1000)
                )
                Picasso.get()
                    .load(track.albumCover)
                    .into(imgAlbumCover)
                Picasso.get()
                    .load(track.albumCover)
                    .transform(BlurTransformation(requireContext()))
                    .into(imgBackground)

                //On btnLocation click
                btnLocation.setOnClickListener {
                    val mapDialog = MapDialog.newInstance(track.location)
                    mapDialog.show(parentFragmentManager, "Map")
                }

                detailsView.visibility = View.VISIBLE
            }
        })

        //Tracking message changes to show message
        trackDetailViewModel.messageArray.observe(viewLifecycleOwner, Observer { messages ->
            binding.pbLoading.visibility = View.GONE

            var finalMessage = ""
            messages.forEach {message ->
                val text = message.asString(requireContext()).trim()
                if (text.isNotEmpty())
                    finalMessage = "$finalMessage $text"
            }
            Snackbar.make(binding.main, finalMessage.trim(), Snackbar.LENGTH_SHORT)
                .setTextColor(requireContext().getColor(R.color.PM_white))
                .setBackgroundTint(requireContext().getColor(R.color.PM_dark_green))
                .setTextMaxLines(3)
                .show()
        })

        //Show track detail at start
        trackId?.let { trackId ->
            trackDetailViewModel.getTrackDetail(trackId)
        }

    }

    override fun onResume() {
        super.onResume()

        if (newFragment){
            binding.detailsView.visibility = View.GONE
            binding.pbLoading.visibility = View.VISIBLE
            newFragment = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(trackId: String) =
            TrackDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(TRACK_ID, trackId)
                }
            }
    }
}