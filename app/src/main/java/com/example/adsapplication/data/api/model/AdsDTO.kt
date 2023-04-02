package com.example.adsapplication.data.api.model

data class AdsDTO(
    val items: List<AdDTO>,
    val size: Int,
    val version: String
)