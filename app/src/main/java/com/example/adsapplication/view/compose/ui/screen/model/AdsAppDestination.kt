package com.example.adsapplication.view.compose.ui.screen.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.adsapplication.R

enum class AdsAppDestination(
    @StringRes val titleResId: Int,
    val imageVector: ImageVector
) {
    ALL(
        titleResId = R.string.ads_of_the_day,
        imageVector = Icons.Sharp.ShoppingCart,
    ),
    FAVOURITE(
        titleResId = R.string.your_favourites,
        imageVector = Icons.Outlined.Favorite,
    )
}