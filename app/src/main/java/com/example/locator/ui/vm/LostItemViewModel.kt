package com.example.locator.ui.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.learnmate.data.room.AppDatabase
import com.example.locator.data.repository.LostItemRepository
import com.example.locator.data.room.entities.LostItem
import kotlinx.coroutines.launch

class LostItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LostItemRepository
    val allLostItems: LiveData<List<LostItem>>
    val allFoundItems: LiveData<List<LostItem>>

    init {
        val lostItemDao = AppDatabase.getDatabase(application).lostItemDao()
        repository = LostItemRepository(lostItemDao)
        allLostItems = repository.allLostItems
        allFoundItems = repository.allFoundItems
    }

    fun insert(item: LostItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: LostItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: LostItem) = viewModelScope.launch {
        repository.delete(item)
    }
}