package com.example.adsapplication.view.compose.ui.screen.all

import com.example.adsapplication.domain.model.Advertisement

class AllAdsUiState(
    val isLoading: Boolean,
    val resetTimer: Boolean?,
    val advertisementsResult: Result<List<Advertisement>>
)