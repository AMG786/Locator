package com.example.locator.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.locator.data.room.entities.LostItem

@Dao
interface LostItemDao {
    @Insert
    suspend fun insert(item: LostItem)

    @Update
    suspend fun update(item: LostItem)

    @Delete
    suspend fun delete(item: LostItem)

    @Query("SELECT * FROM lost_items WHERE isFound = 0 ORDER BY date DESC")
    fun getAllLostItems(): LiveData<List<LostItem>>

    @Query("SELECT * FROM lost_items WHERE isFound = 1 ORDER BY date DESC")
    fun getAllFoundItems(): LiveData<List<LostItem>>
}