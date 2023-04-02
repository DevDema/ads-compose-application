package com.example.adsapplication.domain.datasource.cache

import com.example.adsapplication.domain.model.Advertisement

class AdsCacheDataSourceImpl: AdsCacheDataSource {

    private var advertisementList: List<Advertisement> = emptyList()

    override suspend fun getAds(): List<Advertisement> {
        return advertisementList
    }

    override suspend fun setAds(list: List<Advertisement>) {
        advertisementList = list
    }
}