package com.example.locator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.locator.MainActivity
import com.example.locator.R

/**
Created by Abdul Mueez, 04/24/2025
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<Button>(R.id.create_a_new_advert).setOnClickListener {
            (activity as MainActivity).navigateToFragment(AddItemFragment(), true)
        }
        view.findViewById<Button>(R.id.btnShowOnMap).setOnClickListener {
            (activity as MainActivity).navigateToFragment1(MapFragment())
        }

        view.findViewById<Button>(R.id.show_all_lost_and_found_items).setOnClickListener {
            (activity as MainActivity).navigateToFragment(ItemListFragment(), true)
        }

        return view
    }
}