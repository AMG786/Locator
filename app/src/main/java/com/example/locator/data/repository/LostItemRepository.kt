package com.example.locator.data.repository

import androidx.lifecycle.LiveData
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
}