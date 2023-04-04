@file:OptIn(ExperimentalAnimationApi::class)

package com.example.adsapplication.view.compose.ui.screen.all

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination.ALL
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.allAds() {
    composable(
        route = ALL.route
    ) {
        AllAdsScreen()
    }
}

fun NavController.navigateToAll(navOptions: NavOptions? = null) = navigate(ALL.route, navOptions)