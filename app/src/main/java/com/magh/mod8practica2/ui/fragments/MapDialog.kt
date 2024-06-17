package com.magh.mod8practica2.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.magh.mod8practica2.data.remote.model.LocationDTO
import com.magh.mod8practica2.databinding.DialogMapBinding
import com.magh.mod8practica2.R
import com.magh.mod8practica2.utils.retrieveParcelable

private const val LOCATION = "location"

class MapDialog: DialogFragment() {

    private var _binding: DialogMapBinding? = null
    private val binding get() = _binding!!

    private var location: LocationDTO? = null

    private lateinit var map: GoogleMap

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogMapBinding.inflate(layoutInflater)

        //Read arguments
        arguments?.let { bundle ->
            location = bundle.retrieveParcelable(LOCATION)
        }

        //Map configuration
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            location?.let {
                createMarker(it.latitude!!, it.longitude!!)
            }
        }

        return createDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(location: LocationDTO?) =
            MapDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(LOCATION, location)
                }
            }
    }

    private fun createDialog(): Dialog {

        val mapDialog = MaterialAlertDialogBuilder(requireContext(), R.style.PlayMent_Dialog)
        mapDialog.setView(binding.root)

        mapDialog.setPositiveButton(getString(R.string.label_close)) { dialog, _ ->
            dialog.dismiss()
        }

        return mapDialog.create()

    }

    private fun createMarker(lat: Double, long: Double) {
        val coordinates = LatLng(lat, long)
        val marker = MarkerOptions()
            .position(coordinates)

        map.addMarker(marker)

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 5f),
            4000,
            null
        )
    }

}