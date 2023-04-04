package com.example.adsapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.adsapplication.data.database.model.FavouriteAd
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteAdDao {
    @Query("SELECT * FROM favourite_ads")
    suspend fun getAll(): List<FavouriteAd>

    @Query("SELECT * FROM favourite_ads")
    fun getAllFlow(): Flow<List<FavouriteAd>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ad: FavouriteAd): Long

    @Delete
    suspend fun delete(ad: FavouriteAd): Int
}