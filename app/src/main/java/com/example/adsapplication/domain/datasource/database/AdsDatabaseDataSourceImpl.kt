package com.example.adsapplication.domain.datasource.database

import com.example.adsapplication.data.database.dao.FavouriteAdDao
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.util.converters.toFavouriteDB
import javax.inject.Inject

class AdsDatabaseDataSourceImpl @Inject constructor(
    private val favouriteAdDao: FavouriteAdDao,
): AdsDatabaseDataSource {

    override val flow = favouriteAdDao.getAllFlow()

    override suspend fun getFavourites() =
        favouriteAdDao.getAll()

    override suspend fun insert(advertisement: Advertisement) =
        favouriteAdDao.insert(advertisement.toFavouriteDB())

    override suspend fun delete(advertisement: Advertisement) =
        favouriteAdDao.delete(advertisement.toFavouriteDB())
}