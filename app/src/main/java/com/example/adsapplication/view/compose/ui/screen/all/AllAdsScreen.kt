@file:OptIn(ExperimentalMaterialApi::class)

package com.example.adsapplication.view.compose.ui.screen.all

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adsapplication.R
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.view.compose.ui.components.AdvertisementCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AllAdsScreen() {
    val viewModel = hiltViewModel<AllAdsViewModel>()
    val uiState by viewModel.flow.collectAsState()

    when {
        uiState.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        uiState.advertisementsResult.isSuccess -> {

            AllAdsScreen(
                advertisementList = uiState.advertisementsResult.getOrThrow(),
                onToggleFavourite = viewModel::toggleFavourite,
                onRefresh = viewModel::refresh
            )
        }

        else -> {
            ErrorScreen(uiState.resetTimer, onClickRefresh = viewModel::refresh)
        }
    }
}

@Composable
private fun ErrorScreen(
    resetTimer: Boolean?,
    onClickRefresh: () -> Unit
) {
    var timer by rememberSaveable(resetTimer) { mutableStateOf(10) }

    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000L)
            timer -= 1
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.warning)
            )

            Text(
                text = stringResource(R.string.oh_no),
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                text = stringResource(R.string.loading_ads_error),
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )

            if (resetTimer != null) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.retrying_in, timer),
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            } else {
                TextButton(onClick = onClickRefresh) {
                    Text(text = stringResource(R.string.refresh))
                }
            }
        }
    }
}

@Composable
private fun AllAdsScreen(
    advertisementList: List<Advertisement>,
    onRefresh: () -> Unit,
    onToggleFavourite: (Advertisement) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        onRefresh()
        scope.launch {
            // Usually pull refresh indicators do not give the impression of doing anything
            // unless they are sticking for a bit of time
            isRefreshing = true

            delay((300L..1000L).random())

            isRefreshing = false
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clipToBounds()
            .pullRefresh(pullRefreshState)
    ) {

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(advertisementList) { advertisement ->
                AdvertisementCard(
                    advertisement = advertisement,
                    onToggleFavourite = { onToggleFavourite(advertisement) },
                    showFavouriteIcon = true
                )
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AllAdsScreenPreview() = MaterialTheme {
    AllAdsScreen(
        listOf(
            Advertisement(
                id = "id",
                imageUrl = "",
                description = "A fake advertisement, isn't it beautiful?",
                location = "Oslo, Norway",
                totalPrice = 2000,
                valuePrice = 1500,
                score = 1.0,
                shippingLabel = "Free shipping",
                url = "",
                isFavourite = true,
            ),
            Advertisement(
                id = "id",
                imageUrl = "",
                description = "A second fake advertisement, isn't it beautiful?",
                location = "Bærum, Norway",
                totalPrice = 2000,
                valuePrice = 1500,
                score = 1.0,
                shippingLabel = "Free shipping",
                url = "",
                isFavourite = false,
            )
        ),
        onRefresh = {},
        onToggleFavourite = {}
    )
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ErrorScreenPreview() = MaterialTheme {
    ErrorScreen(resetTimer = false, onClickRefresh = {})
}