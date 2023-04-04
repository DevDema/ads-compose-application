package com.example.adsapplication.domain.repository

import com.example.adsapplication.domain.datasource.api.AdsApiDataSource
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSource
import com.example.adsapplication.domain.datasource.database.AdsDatabaseDataSource
import com.example.adsapplication.domain.model.Advertisement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class AdsRepositoryImpl @Inject constructor(
    private val apiDataSource: AdsApiDataSource,
    private val cacheDataSource: AdsCacheDataSource,
    private val adsDatabaseDataSource: AdsDatabaseDataSource,
) : AdsRepository {

    override val favouritesFlow = adsDatabaseDataSource.flow


    override suspend fun getAds(refresh: Boolean): Result<List<Advertisement>> =
        withContext(Dispatchers.IO) {
            if (!refresh) {
                val cachedList = cacheDataSource.getAds()

                if (cachedList.isNotEmpty()) {
                    return@withContext Result.success(cachedList)
                }
            }

            val apiResult = apiDataSource.getAds()
                .map { it.populateFavourites() }

            if (apiResult.isSuccess) {
                cacheDataSource.setAds(apiResult.getOrThrow())
            }

            return@withContext apiResult
        }

    override suspend fun toggleFavourite(advertisement: Advertisement): Result<Unit> {
        if(advertisement.isFavourite) {
            val numberOfRows = adsDatabaseDataSource.delete(advertisement)

            return if(numberOfRows > 0) {
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("Deletion of ad failed"))
            }
        } else {
            val rowId = adsDatabaseDataSource.insert(advertisement)

            return if(rowId > 0) {
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("Insertion of ad failed"))
            }
        }
    }
    private suspend fun List<Advertisement>.populateFavourites(): List<Advertisement> {
        val favouriteList = adsDatabaseDataSource.getFavourites()

        return map { advertisement ->
            advertisement.copy(
                isFavourite = favouriteList.any { it.id == advertisement.id }
            )
        }
    }


}