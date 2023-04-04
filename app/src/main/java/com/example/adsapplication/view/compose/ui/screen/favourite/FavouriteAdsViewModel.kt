package com.example.adsapplication.view.compose.ui.screen.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.domain.repository.AdsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteAdsViewModel @Inject constructor(
    private val adsRepository: AdsRepository,
): ViewModel() {
    val flow = adsRepository.favouritesFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun removeFavourite(advertisement: Advertisement) {
        viewModelScope.launch {
            adsRepository.toggleFavourite(advertisement)
        }
    }
}