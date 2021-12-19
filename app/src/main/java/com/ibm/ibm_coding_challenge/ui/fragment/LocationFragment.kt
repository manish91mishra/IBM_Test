package com.ibm.ibm_coding_challenge.ui.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibm.ibm_coding_challenge.databinding.FragmentLocationBinding
import com.ibm.ibm_coding_challenge.model.SearchResult
import com.ibm.ibm_coding_challenge.ui.adapter.LocationListAdapter
import com.ibm.ibm_coding_challenge.utils.Constants
import com.ibm.ibm_coding_challenge.viewmodels.LocationViewModel

class LocationFragment : Fragment() {

    private lateinit var locationViewModel: LocationViewModel
    private var _binding: FragmentLocationBinding? = null

    private val binding get() = _binding!!
    private val locationList = ArrayList<SearchResult>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]

        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initializeViews()

        locationViewModel.startLocationUpdates(requireActivity())

        locationViewModel.location.observe(viewLifecycleOwner,
            { location ->
                locationViewModel.stopLocationUpdate()
                getLocationData(false, Constants.EMPTY)
            }
        )

        locationViewModel.locationList.observe(viewLifecycleOwner, {
            locationList.clear()
            locationList.addAll(it)
            binding.recyclerLocations.adapter?.notifyDataSetChanged()

            locationViewModel.insertLocations(it,requireActivity().application)
        })

        return root
    }

    private fun getLocationData(isSearch: Boolean, searchText: String) {
        locationViewModel.getLocations(isSearch, searchText)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationViewModel.PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                locationViewModel.startLocationUpdates(requireActivity())
        }
    }

    private fun initializeViews() {
        binding.searchLocations.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    getLocationData(true, it)
                    locationViewModel.insertQuery(query,requireActivity().application)
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.recyclerLocations.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = LocationListAdapter(requireContext(), locationList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}