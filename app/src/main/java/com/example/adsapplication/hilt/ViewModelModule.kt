package com.example.adsapplication.hilt

import android.content.Context
import androidx.room.Room
import com.example.adsapplication.data.api.client.ads.AdsClient
import com.example.adsapplication.data.api.client.ads.AdsService
import com.example.adsapplication.data.api.client.ads.AdsServiceImpl
import com.example.adsapplication.data.database.AppDatabase
import com.example.adsapplication.data.database.dao.FavouriteAdDao
import com.example.adsapplication.domain.datasource.api.AdsApiDataSource
import com.example.adsapplication.domain.datasource.api.AdsApiDataSourceImpl
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSource
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSourceImpl
import com.example.adsapplication.domain.datasource.database.AdsDatabaseDataSource
import com.example.adsapplication.domain.datasource.database.AdsDatabaseDataSourceImpl
import com.example.adsapplication.domain.repository.AdsRepository
import com.example.adsapplication.domain.repository.AdsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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
    fun provideAdsDatabaseSource(database: AppDatabase): AdsDatabaseDataSource = AdsDatabaseDataSourceImpl(database.favouriteAdDao())

    @Provides
    fun provideAdsRepository(
        apiDataSource: AdsApiDataSource,
        cacheDataSource: AdsCacheDataSource,
        databaseDataSource: AdsDatabaseDataSource
    ): AdsRepository = AdsRepositoryImpl(apiDataSource, cacheDataSource, databaseDataSource)
}