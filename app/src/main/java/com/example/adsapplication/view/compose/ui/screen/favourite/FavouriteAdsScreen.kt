@file:OptIn(ExperimentalMaterialApi::class)

package com.example.adsapplication.view.compose.ui.screen.favourite

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.view.compose.ui.components.AdvertisementCard
import kotlin.math.roundToInt


@Composable
fun FavouriteAdsScreen() {
    val viewModel = hiltViewModel<FavouriteAdsViewModel>()
    val favouriteAds by viewModel.flow.collectAsState()

    FavouriteAdsScreen(
        advertisementList = favouriteAds,
        onRemoveFavourite = viewModel::removeFavourite
    )
}

@Composable
private fun FavouriteAdsScreen(
    advertisementList: List<Advertisement>,
    onRemoveFavourite: (Advertisement) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(advertisementList) { advertisement ->
            AdvertisementCard(
                advertisement = advertisement,
                onToggleFavourite = {},
                onRemoveFavourite = { onRemoveFavourite(advertisement) },
                showFavouriteIcon = false,
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FavouriteAdsScreenPreview() {
    FavouriteAdsScreen()
}