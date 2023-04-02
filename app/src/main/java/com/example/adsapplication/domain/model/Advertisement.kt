package com.example.adsapplication.domain.model

import com.example.adsapplication.data.api.model.Price

class Advertisement(
    val id: String,
    val imageUrl: String,
    val description: String,
    val location: String,
    val totalPrice: Int?,
    val valuePrice: Int?,
    val score: Double,
    val shippingLabel: String,
    val url: String,
)