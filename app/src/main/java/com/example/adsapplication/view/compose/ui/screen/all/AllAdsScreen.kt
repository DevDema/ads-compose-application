@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.example.adsapplication.view.compose.ui.screen.all

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.adsapplication.R
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.util.converters.toReadable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AllAdsScreen() {
    val viewModel = hiltViewModel<AllAdsViewModel>()
    val advertisementResult by viewModel.advertisementFlow.collectAsState()
    val isLoading by viewModel.isLoadingFlow.collectAsState()

    when {
        isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        advertisementResult.isSuccess -> {
            AllAdsScreen(
                advertisementList = advertisementResult.getOrThrow(),
                onRefresh = viewModel::refresh
            )
        }

        else -> {
            ErrorScreen()
        }
    }
}

@Composable
private fun ErrorScreen() {
    var timer by rememberSaveable { mutableStateOf(10) }

    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000L)
            timer -= 1
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                modifier = Modifier.size(100.dp),
                contentDescription = stringResource(R.string.warning)
            )

            Text(
                text = stringResource(R.string.oh_no),
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = stringResource(R.string.loading_ads_error),
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.retrying_in, timer),
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AllAdsScreen(
    advertisementList: List<Advertisement>,
    onRefresh: () -> Unit,
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
                AdvertisementCard(advertisement)
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun AdvertisementCard(advertisement: Advertisement) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier.fillMaxWidth(),
        onClick = {}
    ) {
        Row(
            modifier = Modifier.padding(all = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://images.finncdn.no/dynamic/480x360c/${advertisement.imageUrl}")
                    .diskCacheKey(advertisement.id)
                    .build(),
                contentDescription = advertisement.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .aspectRatio(1f)
            )

            Column {
                Text(
                    text = advertisement.description,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildString {
                        advertisement.valuePrice
                            ?.takeUnless { it == 0 }
                            ?.let { valuePrice ->
                                append(valuePrice.toReadable())

                                advertisement.totalPrice
                                    ?.takeUnless { it == 0 }
                                    ?.let {
                                        append(" / ${advertisement.totalPrice.toReadable()}")
                                    }

                                append(" NOK")
                            } ?: append(stringResource(R.string.free_item))
                    },
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = advertisement.location,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                )
            }
        }
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
            )
        ),
        onRefresh = {}
    )
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ErrorScreenPreview() = MaterialTheme {
    ErrorScreen()
}