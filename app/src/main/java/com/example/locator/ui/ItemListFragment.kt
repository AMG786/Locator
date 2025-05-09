package com.example.locator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locator.MainActivity
import com.example.locator.R
import com.example.locator.databinding.FragmentItemListBinding
import com.example.locator.ui.adapter.ItemAdapter
import com.example.locator.ui.vm.LostItemViewModel

/**
Created by Abdul Mueez, 04/24/2025
 */
class ItemListFragment : Fragment() {
    private lateinit var viewModel: LostItemViewModel
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LostItemViewModel::class.java)

        // Set up RecyclerView
        val adapter = ItemAdapter { item ->
            // Handle item click - navigate to detail view
            (activity as MainActivity).navigateToFragment(ItemDetailFragment.newInstance(item.id))
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // Observe lost items
        viewModel.allLostItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        binding.fabAdd.setOnClickListener {
            (activity as MainActivity).navigateToFragment(AddItemFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}