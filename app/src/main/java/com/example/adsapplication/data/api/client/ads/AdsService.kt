package com.example.adsapplication.data.api.client.ads

import com.example.adsapplication.data.api.model.AdsDTO
import retrofit2.Response

interface AdsService {

    suspend fun getAds(): Response<AdsDTO>
}