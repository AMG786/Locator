package com.example.learnmate.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.locator.data.room.dao.LostItemDao
import com.example.locator.data.room.entities.LostItem

/**
Created by Abdul Mueez, 04/24/2025
 */
@Database(entities = [LostItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lostItemDao(): LostItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lost_found_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
