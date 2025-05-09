package com.example.locator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.locator.R
import com.example.locator.databinding.FragmentItemDetailBinding
import com.example.locator.ui.vm.LostItemViewModel

/**
Created by Abdul Mueez, 04/24/2025
 */
class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LostItemViewModel
    private var itemId: Int = -1

    companion object {
        fun newInstance(itemId: Int) = ItemDetailFragment().apply {
            arguments = Bundle().apply {
                putInt("item_id", itemId)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LostItemViewModel::class.java)
        itemId = arguments?.getInt("item_id") ?: -1

        viewModel.allLostItems.observe(viewLifecycleOwner) { items ->
            val item = items.find { it.id == itemId }
            item?.let {
                binding.tvTitle.text = it.title
                binding.tvDescription.text = it.description
                binding.tvLocation.text = it.location
                binding.tvDate.text = it.date
                binding.tvContact.text = it.contact

                binding.btnMarkFound.setOnClickListener {
                    val updatedItem = item.copy(isFound = true, isLost = false)
                    viewModel.update(updatedItem)
                    activity?.onBackPressed()
                }

                binding.btnDelete.setOnClickListener {
                    viewModel.delete(item)
                    activity?.onBackPressed()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}