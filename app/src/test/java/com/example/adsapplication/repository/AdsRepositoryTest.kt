package com.example.adsapplication.repository

import com.example.adsapplication.domain.datasource.api.AdsApiDataSource
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSource
import com.example.adsapplication.domain.repository.AdsRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.should
import org.amshove.kluent.shouldHaveSize
import org.junit.Test


class AdsRepositoryTest {

    @Test
    fun fetchCachedAdsTest() {
        val cacheDataSource = mockk<AdsCacheDataSource>()
        val apiDataSource = mockk<AdsApiDataSource>()

        coEvery { cacheDataSource.getAds() } returns listOf(mockk())
        coEvery { apiDataSource.getAds() } returns Result.failure(IllegalStateException("Intentional exception"))

        val adsRepository = AdsRepositoryImpl(apiDataSource, cacheDataSource)

        runBlocking {
            val advertisementResult = adsRepository.getAds(refresh = false)
            advertisementResult.should { isSuccess }
            val actualList = advertisementResult.getOrThrow()

            actualList.shouldHaveSize(1)
        }
    }

    @Test
    fun fetchApiAdsTest() {
        val cacheDataSource = mockk<AdsCacheDataSource>()
        val apiDataSource = mockk<AdsApiDataSource>()

        coEvery { cacheDataSource.getAds() } returns emptyList()
        coEvery { cacheDataSource.setAds(any()) } answers {}
        coEvery { apiDataSource.getAds() } returns Result.success(listOf(mockk()))

        val adsRepository = AdsRepositoryImpl(apiDataSource, cacheDataSource)

        runBlocking {
            val advertisementResult = adsRepository.getAds(refresh = false)
            advertisementResult.should { isSuccess }
            val actualList = advertisementResult.getOrThrow()

            actualList.shouldHaveSize(1)
        }
    }

    @Test
    fun fetchForceRefreshAdsTest() {
        val cacheDataSource = mockk<AdsCacheDataSource>()
        val apiDataSource = mockk<AdsApiDataSource>()

        coEvery { cacheDataSource.getAds() } returns emptyList()
        coEvery { cacheDataSource.setAds(any()) } answers {}
        coEvery { apiDataSource.getAds() } returns Result.success(listOf(mockk()))

        val adsRepository = AdsRepositoryImpl(apiDataSource, cacheDataSource)

        runBlocking {
            val advertisementResult = adsRepository.getAds(refresh = true)
            advertisementResult.should { isSuccess }
            val actualList = advertisementResult.getOrThrow()

            actualList.shouldHaveSize(1)
        }
    }
}