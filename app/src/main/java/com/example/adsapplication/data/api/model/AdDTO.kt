package com.example.adsapplication.data.api.model

import com.google.gson.annotations.SerializedName

data class AdDTO(
    val id: String,
    @SerializedName("ad-type")
    val adType: String,
    val description: String? = null,
    val favourite: Favourite? = null,
    val image: Image? = null,
    val location: String? = null,
    val price: Price? = null,
    val score: Double? = null,
    val shippingOption: ShippingOption? = null,
    val type: String? = null,
    val url: String? = null,
    val version: String? = null
)