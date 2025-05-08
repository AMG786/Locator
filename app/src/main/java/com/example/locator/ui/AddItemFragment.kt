package com.example.locator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.locator.R
import com.example.locator.data.room.entities.LostItem
import com.example.locator.databinding.FragmentAddItemBinding
import com.example.locator.ui.vm.LostItemViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddItemFragment : Fragment() {
    private lateinit var viewModel: LostItemViewModel
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LostItemViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            val title = binding.etName.text.toString()
            val description = binding.etDescription.text.toString()
            val location = binding.etLocation.text.toString()
            val contact = binding.etPhone.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val newItem = LostItem(
                    title = title,
                    description = description,
                    location = location,
                    contact = contact,
                    date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                    isLost = binding.radioLost.isChecked,
                    isFound = !binding.radioLost.isChecked
                )

                viewModel.insert(newItem)
                activity?.onBackPressed()
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}