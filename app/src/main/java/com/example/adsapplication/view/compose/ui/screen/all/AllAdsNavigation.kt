@file:OptIn(ExperimentalAnimationApi::class)

package com.example.adsapplication.view.compose.ui.screen.all

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination.ALL
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.allAds() {
    composable(
        route = ALL.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popExitTransition = { exitTransition() }
    ) {
        AllAdsScreen()
    }
}

private fun AnimatedContentScope<NavBackStackEntry>.exitTransition() =
    if (targetState.destination.route == AdsAppDestination.FAVOURITE.route) {
        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
    } else {
        fadeOut()
    }

private fun AnimatedContentScope<NavBackStackEntry>.enterTransition() =
    if (initialState.destination.route == AdsAppDestination.FAVOURITE.route) {
        slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
    } else {
        fadeIn()
    }

fun NavController.navigateToAll(navOptions: NavOptions? = null) = navigate(ALL.route, navOptions)