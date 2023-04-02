package com.example.adsapplication.domain.datasource.cache

import com.example.adsapplication.domain.model.Advertisement

interface AdsCacheDataSource {

    suspend fun getAds(): List<Advertisement>
    suspend fun setAds(list: List<Advertisement>)
}