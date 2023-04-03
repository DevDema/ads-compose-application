@file:OptIn(ExperimentalAnimationApi::class)

package com.example.adsapplication.view.compose.ui.screen.favourite

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination
import com.google.accompanist.navigation.animation.composable

private const val ROUTE = "favourite_ads"
fun NavGraphBuilder.favouriteAds(): AdsAppDestination {
    composable(
        route = ROUTE
    ) {
        FavouriteAdsScreen()
    }

    return AdsAppDestination.FAVOURITE
}

fun NavController.navigateToFavourites(navOptions: NavOptions? = null) = navigate(ROUTE, navOptions)
