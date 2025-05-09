package com.example.locator.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.locator.data.room.dao.LostItemDao
import com.example.locator.data.room.entities.LostItem

class LostItemRepository(private val lostItemDao: LostItemDao) {
    val allLostItems: LiveData<List<LostItem>> = lostItemDao.getAllLostItems()
    val allFoundItems: LiveData<List<LostItem>> = lostItemDao.getAllFoundItems()

    suspend fun insert(item: LostItem) {
        lostItemDao.insert(item)
    }

    suspend fun update(item: LostItem) {
        lostItemDao.update(item)
    }

    suspend fun delete(item: LostItem) {
        lostItemDao.delete(item)
    }

    // Combine both LiveData sources
    fun getAllItems(): LiveData<List<LostItem>> {
        return MediatorLiveData<List<LostItem>>().apply {
            var lastLostItems: List<LostItem> = emptyList()
            var lastFoundItems: List<LostItem> = emptyList()

            fun updateCombined() {
                value = lastLostItems + lastFoundItems
            }

            addSource(allLostItems) { lostItems ->
                lastLostItems = lostItems ?: emptyList()
                updateCombined()
            }

            addSource(allFoundItems) { foundItems ->
                lastFoundItems = foundItems ?: emptyList()
                updateCombined()
            }
        }
    }
}