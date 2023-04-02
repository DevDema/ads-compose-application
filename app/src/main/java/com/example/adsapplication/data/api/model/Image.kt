package com.example.adsapplication.data.api.model

data class Image(
    val width: Int,
    val height: Int,
    val scalable: Boolean,
    val type: String,
    val url: String,
)