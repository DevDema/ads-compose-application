package com.example.adsapplication.domain.datasource.database

import com.example.adsapplication.data.database.model.FavouriteAd
import com.example.adsapplication.domain.model.Advertisement
import kotlinx.coroutines.flow.Flow

interface AdsDatabaseDataSource {

    val flow: Flow<List<FavouriteAd>>
    suspend fun getFavourites(): List<FavouriteAd>
    suspend fun insert(advertisement: Advertisement): Long

    suspend fun delete(advertisement: Advertisement): Int
}