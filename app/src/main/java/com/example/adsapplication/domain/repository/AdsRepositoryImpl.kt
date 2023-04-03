package com.example.adsapplication.domain.repository

import com.example.adsapplication.domain.datasource.api.AdsApiDataSource
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSource
import com.example.adsapplication.domain.model.Advertisement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(
    private val apiDataSource: AdsApiDataSource,
    private val cacheDataSource: AdsCacheDataSource,
): AdsRepository {

    override suspend fun getAds(refresh: Boolean): Result<List<Advertisement>> = withContext(Dispatchers.IO) {
        if(!refresh) {
            val cachedList = cacheDataSource.getAds()

            if (cachedList.isNotEmpty()) {
                return@withContext Result.success(cachedList)
            }
        }

        val apiResult = apiDataSource.getAds()

        if(apiResult.isSuccess) {
            cacheDataSource.setAds(apiResult.getOrThrow())
        }

        return@withContext apiResult
    }
}