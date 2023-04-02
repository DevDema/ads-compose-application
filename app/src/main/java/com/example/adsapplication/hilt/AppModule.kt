package com.example.adsapplication.hilt

import android.content.Context
import androidx.room.Room
import com.example.adsapplication.data.api.client.ads.AdsClient
import com.example.adsapplication.data.api.client.ads.AdsService
import com.example.adsapplication.data.api.client.ads.AdsServiceImpl
import com.example.adsapplication.domain.datasource.api.AdsApiDataSourceImpl
import com.example.adsapplication.data.database.AppDatabase
import com.example.adsapplication.domain.datasource.api.AdsApiDataSource
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSource
import com.example.adsapplication.domain.datasource.cache.AdsCacheDataSourceImpl
import com.example.adsapplication.domain.repository.AdsRepository
import com.example.adsapplication.domain.repository.AdsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindAdsApiDataSource(
        dataSource: AdsApiDataSourceImpl
    ): AdsApiDataSource

    @Binds
    abstract fun bindAdsCacheDataSource(
        dataSource: AdsCacheDataSourceImpl
    ): AdsCacheDataSource
    @Binds
    abstract fun bindAdsService(
        service: AdsServiceImpl
    ): AdsService

    @Binds
    abstract fun bindAdsRepository(
        repository: AdsRepositoryImpl
    ): AdsRepository

    companion object {
        @Provides
        fun provideAdsRetrofit(): AdsClient {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AdsClient.baseUrl)
                .build()
                .create(AdsClient::class.java)
        }

        @Provides
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "ads_database"
            ).build()
        }
    }
}