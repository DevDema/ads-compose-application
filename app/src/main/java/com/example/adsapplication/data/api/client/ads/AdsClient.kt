package com.example.adsapplication.data.api.client.ads

import com.example.adsapplication.data.api.model.AdsDTO
import retrofit2.Response
import retrofit2.http.GET

interface AdsClient {

    @GET("baldermork/6a1bcc8f429dcdb8f9196e917e5138bd//raw/discover.json")
    suspend fun getAds(): Response<AdsDTO>

    companion object {
        val baseUrl =
            "https://gist.githubusercontent.com/"
    }
}