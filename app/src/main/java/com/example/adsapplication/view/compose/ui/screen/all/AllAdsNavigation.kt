@file:OptIn(ExperimentalAnimationApi::class)

package com.example.adsapplication.view.compose.ui.screen.all

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination.ALL
import com.google.accompanist.navigation.animation.composable

const val ALL_ADS_ROUTE = "all_ads"
fun NavGraphBuilder.allAds(): AdsAppDestination {
    composable(
        route = ALL_ADS_ROUTE
    ) {
        AllAdsScreen()
    }

    return ALL
}

fun NavController.navigateToAll(navOptions: NavOptions? = null) = navigate(ALL_ADS_ROUTE, navOptions)