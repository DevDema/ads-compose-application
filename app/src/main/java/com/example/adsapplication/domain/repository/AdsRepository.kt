package com.example.adsapplication.domain.repository

import com.example.adsapplication.data.database.model.FavouriteAd
import com.example.adsapplication.domain.model.Advertisement
import kotlinx.coroutines.flow.Flow

interface AdsRepository {

    val favouritesFlow: Flow<List<FavouriteAd>>

    suspend fun getAds(refresh: Boolean): Result<List<Advertisement>>

    suspend fun toggleFavourite(advertisement: Advertisement): Result<Unit>
}