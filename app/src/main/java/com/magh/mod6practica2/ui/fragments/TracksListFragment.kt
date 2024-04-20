package com.magh.mod6practica2.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.magh.mod6practica2.R
import com.magh.mod6practica2.application.PlayMentApp
import com.magh.mod6practica2.databinding.FragmentTracksListBinding
import com.magh.mod6practica2.ui.adapters.TrackAdapter
import com.magh.mod6practica2.ui.viewmodels.TracksListViewModel
import com.magh.mod6practica2.ui.viewmodels.TracksListViewModelFactory

class TracksListFragment : Fragment() {

    private var _binding: FragmentTracksListBinding? = null
    private val binding get() = _binding!!

    //Variables for MVVM
    private val tracksListViewModel: TracksListViewModel by activityViewModels {
        TracksListViewModelFactory(
            (activity?.application as PlayMentApp).repository //Repository
        )
    }

    //Variables for tracks list
    private lateinit var trackAdapter: TrackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =FragmentTracksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Configure tracks list
        trackAdapter = TrackAdapter()
        //Click on track
        trackAdapter.onTrackClicked = {track ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,TrackDetailFragment.newInstance(track.id.toString()))
                .addToBackStack(null)
                .commit()
        }
        binding.tracksList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = trackAdapter
        }

        //Tracking tracks changes to update UI
        tracksListViewModel.tracks.observe(viewLifecycleOwner, Observer {tracks ->
            binding.pbLoading.visibility = View.GONE

            trackAdapter.updateList(tracks)
        })

        //Tracking message changes to show message
        tracksListViewModel.messageArray.observe(viewLifecycleOwner, Observer { messages ->
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

        //Show all tracks at start
        tracksListViewModel.getAllTracks()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TracksListFragment()
    }

}