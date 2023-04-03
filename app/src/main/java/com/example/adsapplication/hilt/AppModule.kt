package com.example.adsapplication.hilt

import android.content.Context
import androidx.room.Room
import com.example.adsapplication.data.api.client.ads.AdsClient
import com.example.adsapplication.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAdsRetrofit(): AdsClient {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AdsClient.baseUrl)
            .build()
            .create(AdsClient::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "ads_database"
        ).build()
    }
}