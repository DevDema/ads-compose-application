package com.example.adsapplication.domain.repository

import com.example.adsapplication.data.database.model.FavouriteAd
import com.example.adsapplication.domain.datasource.api.AdsApiDataSource
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSource
import com.example.adsapplication.domain.datasource.database.AdsDatabaseDataSource
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.util.converters.toAdvertisement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(
    private val apiDataSource: AdsApiDataSource,
    private val cacheDataSource: AdsCacheDataSource,
    private val adsDatabaseDataSource: AdsDatabaseDataSource,
) : AdsRepository {

    override val favouritesFlow = adsDatabaseDataSource.flow
        .map { list ->
            list
                .map(FavouriteAd::toAdvertisement)
                .sortedByDescending { it.score }
        }


    override suspend fun getAds(refresh: Boolean): Result<List<Advertisement>> =
        withContext(Dispatchers.IO) {
            if (!refresh) {
                val cachedList = cacheDataSource.getAds()

                if (cachedList.isNotEmpty()) {
                    return@withContext Result.success(cachedList)
                }
            }

            val apiResult = apiDataSource.getAds()
                .map { list ->
                    list.populateFavourites()
                        .sortedByDescending { it.score }
                }



            if (apiResult.isSuccess) {
                cacheDataSource.setAds(apiResult.getOrThrow())
            }

            return@withContext apiResult
        }

    override suspend fun toggleFavourite(advertisement: Advertisement): Result<Unit> {
        if (advertisement.isFavourite) {
            val numberOfRows = adsDatabaseDataSource.delete(advertisement)

            return if (numberOfRows > 0) {
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("Deletion of ad failed"))
            }
        } else {
            val rowId = adsDatabaseDataSource.insert(advertisement)

            return if (rowId > 0) {
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