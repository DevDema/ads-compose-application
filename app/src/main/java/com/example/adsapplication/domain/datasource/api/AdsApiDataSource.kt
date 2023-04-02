package com.example.adsapplication.domain.datasource.api

import com.example.adsapplication.domain.model.Advertisement

interface AdsApiDataSource {

    suspend fun getAds(): Result<List<Advertisement>>
}