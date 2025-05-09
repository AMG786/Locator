package com.example.locator.ui.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.learnmate.data.room.AppDatabase
import com.example.locator.data.repository.LostItemRepository
import com.example.locator.data.room.entities.LostItem
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.launch

/**
Created by Abdul Mueez, 04/24/2025
 */
class LostItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LostItemRepository
    val allLostItems: LiveData<List<LostItem>>
    val allFoundItems: LiveData<List<LostItem>>
    val allItems: LiveData<List<LostItem>>

    init {
        val lostItemDao = AppDatabase.getDatabase(application).lostItemDao()
        repository = LostItemRepository(lostItemDao)
        allLostItems = repository.allLostItems
        allFoundItems = repository.allFoundItems
        allItems = repository.getAllItems()
    }

    fun insert(item: LostItem, place: Place?) {
        val newItem = place?.let {
            item.copy(
                latitude = it.latLng?.latitude,
                longitude = it.latLng?.longitude
            )
        } ?: item

        viewModelScope.launch {
            repository.insert(newItem)
        }
    }

    fun update(item: LostItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: LostItem) = viewModelScope.launch {
        repository.delete(item)
    }
}