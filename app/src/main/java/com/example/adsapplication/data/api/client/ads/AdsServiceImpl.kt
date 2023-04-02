package com.example.adsapplication.data.api.client.ads

import javax.inject.Inject

class AdsServiceImpl @Inject constructor(
    private val adsClient: AdsClient
): AdsService {

    override suspend fun getAds() = adsClient.getAds()
}