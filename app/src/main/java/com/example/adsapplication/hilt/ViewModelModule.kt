package com.example.adsapplication.hilt

import android.content.Context
import androidx.room.Room
import com.example.adsapplication.data.api.client.ads.AdsClient
import com.example.adsapplication.data.api.client.ads.AdsService
import com.example.adsapplication.data.api.client.ads.AdsServiceImpl
import com.example.adsapplication.data.database.AppDatabase
import com.example.adsapplication.domain.datasource.api.AdsApiDataSource
import com.example.adsapplication.domain.datasource.api.AdsApiDataSourceImpl
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSource
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSourceImpl
import com.example.adsapplication.domain.repository.AdsRepository
import com.example.adsapplication.domain.repository.AdsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {


    @Provides
    fun provideAdsApiDataSource(service: AdsService): AdsApiDataSource =
        AdsApiDataSourceImpl(service)

    @Provides
    fun provideAdsCacheDataSource(): AdsCacheDataSource = AdsCacheDataSourceImpl()

    @Provides
    fun provideAdsService(adsClient: AdsClient): AdsService = AdsServiceImpl(adsClient)

    @Provides
    fun provideAdsRepository(
        apiDataSource: AdsApiDataSource,
        cacheDataSource: AdsCacheDataSource,
    ): AdsRepository = AdsRepositoryImpl(apiDataSource, cacheDataSource)
}