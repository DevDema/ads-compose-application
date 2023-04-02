package com.example.adsapplication.domain.repository

import com.example.adsapplication.domain.model.Advertisement

interface AdsRepository {

    suspend fun getAds(refresh: Boolean): Result<List<Advertisement>>
}