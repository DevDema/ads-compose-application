package com.example.adsapplication.view.compose.ui.screen.favourite

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavouriteAdsScreen() {
    Box(modifier = Modifier.fillMaxSize())
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FavouriteAdsScreenPreview() {
    FavouriteAdsScreen()
}