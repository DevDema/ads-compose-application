package com.example.adsapplication.repository

import com.example.adsapplication.data.api.client.ads.AdsService
import com.example.adsapplication.data.api.model.AdDTO
import com.example.adsapplication.data.api.model.AdsDTO
import com.example.adsapplication.domain.datasource.api.AdsApiDataSourceImpl
import com.example.adsapplication.domain.datasource.api.AdsApiDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.should
import org.amshove.kluent.shouldHaveSize
import org.junit.Test
import retrofit2.Response


class AdsApiDataSourceTest {

    @Test
    fun fetchMinimalAdsTest() {
        val adsService = mockk<AdsService>()
        val adsApiDataSource: AdsApiDataSource = AdsApiDataSourceImpl(adsService)
        val list = buildList {
            repeat(10) { iteration ->
                add(
                    AdDTO(
                        id = iteration.toString(),
                        adType = "type$iteration",
                    )
                )
            }
        }
        val response = Response.success(
            AdsDTO(
                items = list,
                size = list.size,
                version = "whatever"
            )
        )

        coEvery { adsService.getAds() } returns response

        runBlocking {
            val advertisementResult = adsApiDataSource.getAds()
            advertisementResult.should { isSuccess }
            val actualList = advertisementResult.getOrThrow()

            actualList.shouldHaveSize(10)
            actualList.should { all { it.id.isNotEmpty() } }
        }
    }


    @Test
    fun fetchAdsFail() {
        val adsService = mockk<AdsService>()
        val adsApiDataSource: AdsApiDataSource = AdsApiDataSourceImpl(adsService)
        val response = Response.error<AdsDTO>(500, "Intentional Error".toResponseBody("text/plain".toMediaTypeOrNull()))

        coEvery { adsService.getAds() } returns response

        runBlocking {
            val advertisementResult = adsApiDataSource.getAds()
            advertisementResult.should { isFailure }
        }
    }
}