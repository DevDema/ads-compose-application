package com.example.adsapplication.domain.datasource.api

import com.example.adsapplication.data.api.client.ads.AdsService
import com.example.adsapplication.data.api.model.AdDTO
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.util.converters.toAdvertisement
import javax.inject.Inject

class AdsApiDataSourceImpl @Inject constructor(
    private val adsService: AdsService,
): AdsApiDataSource {

    override suspend fun getAds(): Result<List<Advertisement>> {
        val errorBody = adsService.getAds().errorBody()

        if(errorBody != null) {
            return Result.failure(IllegalStateException(errorBody.string()))
        }

        val adsDto = adsService.getAds().body() ?: return Result.success(emptyList())

        return Result.success(adsDto.items.map(AdDTO::toAdvertisement))
    }
}