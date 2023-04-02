package com.example.adsapplication.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_ads")
data class FavouriteAd(
    @PrimaryKey val id: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val totalPrice: Int?,
    val valuePrice: Int?,
    val score: Double,
    val shippingLabel: String,
    val url: String,
)