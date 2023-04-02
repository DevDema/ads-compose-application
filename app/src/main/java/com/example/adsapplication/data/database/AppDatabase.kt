package com.example.adsapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.adsapplication.data.database.dao.FavouriteAdDao
import com.example.adsapplication.data.database.model.FavouriteAd

@Database(entities = [FavouriteAd::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteAdDao(): FavouriteAdDao
}