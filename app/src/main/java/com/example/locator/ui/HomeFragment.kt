package com.example.locator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.locator.MainActivity
import com.example.locator.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
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

        view.findViewById<Button>(R.id.show_all_lost_and_found_items).setOnClickListener {
            (activity as MainActivity).navigateToFragment(ItemListFragment(), true)
        }

        return view
    }
}