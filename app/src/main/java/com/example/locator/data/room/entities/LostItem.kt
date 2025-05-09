package com.example.locator.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
Created by Abdul Mueez, 04/24/2025
 */
@Entity(tableName = "lost_items")
data class LostItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val location: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val date: String,
    val contact: String,
    val isFound: Boolean = false,
    val isLost: Boolean = true,
    val imageUri: String? = null
)