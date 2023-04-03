package com.example.adsapplication.util.converters

import com.example.adsapplication.data.database.model.FavouriteAd
import com.example.adsapplication.domain.model.Advertisement

fun FavouriteAd.toAdvertisement() = Advertisement(
    id = id,
    imageUrl = imageUrl,
    description = description,
    location = location,
    totalPrice = totalPrice,
    valuePrice = valuePrice,
    score = score,
    shippingLabel = shippingLabel,
    url = url,
    isFavourite = true,
)