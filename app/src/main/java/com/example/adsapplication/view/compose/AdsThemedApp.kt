@file:OptIn(ExperimentalAnimationApi::class)

package com.example.adsapplication.view.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.example.adsapplication.view.compose.ui.navigation.MainNavigation
import com.example.adsapplication.view.compose.ui.screen.all.allAds
import com.example.adsapplication.view.compose.ui.screen.all.navigateToAll
import com.example.adsapplication.view.compose.ui.screen.favourite.favouriteAds
import com.example.adsapplication.view.compose.ui.screen.favourite.navigateToFavourites
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

val NavController.bottomNavOptions
    get() = navOptions {
        currentDestination?.let {
            popUpTo(it.id) {
                inclusive = true
                saveState = true
            }
        }

        launchSingleTop = true
        restoreState = true
    }

@Composable
fun AdsThemedApp() {
    val lightColors = lightColorScheme(
        background = Color(0xFFdcdcd8)
    )
    val darkColors = darkColorScheme(
        background = Color(0xFF5a595b)
    )

    MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColors else lightColors) {
        Surface(Modifier.fillMaxSize()) {
            AppScreen()
        }
    }
}

@Composable
fun AppScreen() {
    Column(Modifier.fillMaxSize()) {
        val navController = rememberAnimatedNavController()
        val selectedItem by navController.currentBackStackEntryAsState()

        AnimatedNavHost(
            navController = navController,
            startDestination = ALL.route,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            allAds()

            favouriteAds()
        }

        MainNavigation(
            currentRoute = selectedItem?.destination?.route,
            onClickItem = { item ->

                when (item) {
                    ALL -> navController.navigateToAll(navController.bottomNavOptions)
                    FAVOURITE -> navController.navigateToFavourites(navController.bottomNavOptions)
                }
            }
        )
    }
}
