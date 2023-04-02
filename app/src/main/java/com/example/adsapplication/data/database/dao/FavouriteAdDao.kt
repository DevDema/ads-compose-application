package com.example.adsapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.adsapplication.data.database.model.FavouriteAd

@Dao
interface FavouriteAdDao {
    @Query("SELECT * FROM favourite_ads")
    suspend fun getAll(): List<FavouriteAd>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ad: FavouriteAd)

    @Delete
    suspend fun delete(ad: FavouriteAd)
}