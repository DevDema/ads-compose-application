@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.example.adsapplication.view.compose.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.adsapplication.R
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.util.converters.toReadable
import kotlin.math.roundToInt

@Composable
fun AdvertisementCard(
    advertisement: Advertisement,
    onToggleFavourite: () -> Unit,
    showFavouriteIcon: Boolean,
    onRemoveFavourite: (() -> Unit)? = null,
) {

    var sizePx by remember { mutableStateOf(1f) }
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val anchors = mapOf(
        0f to 0, sizePx to 1
    )

    onRemoveFavourite?.let { callback ->
        LaunchedEffect(swipeableState.currentValue) {
            if (swipeableState.currentValue == 1) {
                callback()
            }
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onRemoveFavourite != null)
                    Modifier.swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        orientation = Orientation.Horizontal,
                    )
                else Modifier
            )
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
            .onSizeChanged {
                sizePx = it.width.toFloat()
            },
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

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.weight(1f)) {
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

                    if (showFavouriteIcon) {
                        FavouriteIconButton(advertisement, onToggleFavourite)
                    }
                }
            }
        }

    }
}

@Composable
private fun FavouriteIconButton(
    advertisement: Advertisement,
    onToggleFavourite: () -> Unit
) {
    if (advertisement.isFavourite) {
        TextButton(
            onClick = onToggleFavourite,
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color.Red,
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                modifier = Modifier.align(Alignment.Bottom),
                contentDescription = stringResource(R.string.remove_from_favourites),
            )
        }
    } else {
        TextButton(onClick = onToggleFavourite) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                modifier = Modifier.align(Alignment.Bottom),
                contentDescription = stringResource(R.string.add_to_favourites)
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AdvertisementCardPreview() = MaterialTheme {
    val isFavourite by remember { mutableStateOf(false) }
    val advertisement by remember {
        derivedStateOf {
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
                isFavourite = isFavourite,
            )
        }
    }

    AdvertisementCard(
        advertisement = advertisement,
        onToggleFavourite = {},
        showFavouriteIcon = true,

        )
}