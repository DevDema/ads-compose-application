package com.example.adsapplication.view.compose.ui.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination
import com.example.adsapplication.view.compose.ui.screen.model.AdsAppDestination.ALL

@Composable
fun MainNavigation(
    currentRoute: String?,
    modifier: Modifier = Modifier,
    onClickItem: (AdsAppDestination) -> Unit
) {
    NavigationBar(modifier.fillMaxWidth()) {
        AdsAppDestination.values().forEach { item ->
            val title = stringResource(item.titleResId)
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = { onClickItem(item) },
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
    MainNavigation(currentRoute = ALL.route, onClickItem = {})
}