package com.example.adsapplication.view.compose.ui.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    onClickItem: (AdsAppDestination) -> Unit
) {
    var selectedItem by rememberSaveable { mutableStateOf(AdsAppDestination.ALL) }

    NavigationBar(modifier.fillMaxWidth()) {
        AdsAppDestination.values().forEach { item ->
            val title = stringResource(item.titleResId)
            NavigationBarItem(
                selected = item == selectedItem,
                onClick = {
                    selectedItem = item
                    onClickItem(item)
                },
                icon = {
                    Icon(
                        imageVector = item.imageVector,
                        contentDescription = title
                    )
                },
                label = {
                    Text(text = title)
                }
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainNavigationPreview() {
    MainNavigation(onClickItem = {})
}