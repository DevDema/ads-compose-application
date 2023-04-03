package com.example.adsapplication.util.converters

import com.example.adsapplication.data.api.model.AdDTO
import com.example.adsapplication.domain.model.Advertisement

fun AdDTO.toAdvertisement() = Advertisement(
    id = id,
    imageUrl = image?.url.orEmpty(),
    description = description.orEmpty(),
    location = location.orEmpty(),
    totalPrice = price?.total,
    valuePrice  = price?.value,
    score = score ?: 0.0,
    shippingLabel = shippingOption?.label.orEmpty(),
    url = url.orEmpty(),
    isFavourite = false
)