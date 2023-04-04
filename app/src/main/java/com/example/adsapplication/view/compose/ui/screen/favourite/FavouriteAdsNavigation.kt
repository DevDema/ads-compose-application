@file:OptIn(ExperimentalAnimationApi::class)

package com.example.adsapplication.view.compose.ui.screen.favourite

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination.ALL
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination.FAVOURITE
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.favouriteAds() {
    composable(
        route = FAVOURITE.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popExitTransition = { exitTransition() }
    ) {
        FavouriteAdsScreen()
    }
}

private fun AnimatedContentScope<NavBackStackEntry>.exitTransition() =
    if (targetState.destination.route == ALL.route) {
        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
    } else {
        fadeOut()
    }

private fun AnimatedContentScope<NavBackStackEntry>.enterTransition() =
    if (initialState.destination.route == ALL.route) {
        slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
    } else {
        fadeIn()
    }

fun NavController.navigateToFavourites(navOptions: NavOptions? = null) = navigate(FAVOURITE.route, navOptions)
