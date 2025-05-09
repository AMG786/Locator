package com.example.locator.ui

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModelProvider
import com.example.locator.R
import com.example.locator.data.room.entities.LostItem
import com.example.locator.databinding.FragmentMapBinding
import com.example.locator.ui.vm.LostItemViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

/**
Created by Abdul Mueez, 04/24/2025
 */
class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private lateinit var viewModel: LostItemViewModel
    private var isMapReady = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(LostItemViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Observe the combined LiveData
        viewModel.allItems.observe(viewLifecycleOwner) { items ->
            if (isMapReady) {
                addMarkersToMap(items)
            }
        }

    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        isMapReady = true
        map.uiSettings.isMyLocationButtonEnabled = false
        map.isMyLocationEnabled = false
        // Configure map settings
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMapToolbarEnabled = false

        // Trigger marker update with current data
        viewModel.allItems.value?.let { items ->
            addMarkersToMap(items)
        }
    }

    private fun addMarkersToMap(items: List<LostItem>) {
        map.clear()

        items.filter { it.latitude != null && it.longitude != null }.forEach { item ->
            val marker = map.addMarker(
                MarkerOptions()
                    .position(LatLng(item.latitude!!, item.longitude!!))
                    .title(item.title)
                    .snippet("${if (item.isLost) "Lost" else "Found"}: ${item.description}")
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            if (item.isLost) BitmapDescriptorFactory.HUE_RED
                            else BitmapDescriptorFactory.HUE_BLUE
                        )
                    )
            )
            marker?.tag = item.id
        }

        // Zoom to show all markers if available
        val validItems = items.filter { it.latitude != null && it.longitude != null }
        if (validItems.isNotEmpty()) {
            val bounds = LatLngBounds.builder()
            validItems.forEach { bounds.include(LatLng(it.latitude!!, it.longitude!!)) }
            try {
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
            } catch (e: Exception) {
                // Handle exception if bounds are invalid
                val firstItem = validItems.first()
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(firstItem.latitude!!, firstItem.longitude!!), 12f))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isMapReady= false;
        _binding = null
    }
}