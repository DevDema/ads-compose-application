@file:OptIn(ExperimentalAnimationApi::class)

package com.example.adsapplication.view.compose.ui.screen.favourite

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination.FAVOURITE
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.favouriteAds() {
    composable(
        route = FAVOURITE.route
    ) {
        FavouriteAdsScreen()
    }
}

fun NavController.navigateToFavourites(navOptions: NavOptions? = null) = navigate(FAVOURITE.route, navOptions)
