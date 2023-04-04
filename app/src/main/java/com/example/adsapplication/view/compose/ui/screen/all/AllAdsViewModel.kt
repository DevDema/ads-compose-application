package com.example.adsapplication.view.compose.ui.screen.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.domain.repository.AdsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AllAdsViewModel @Inject constructor(
    private val adsRepository: AdsRepository,
) : ViewModel() {
    private val advertisementsFlow = MutableStateFlow(Result.success(emptyList<Advertisement>()))
    private val isLoadingFlow = MutableStateFlow(false)
    private val resetTimerFlow = MutableStateFlow<Boolean?>(false)

    val flow = combine(
        adsRepository.favouritesFlow,
        advertisementsFlow,
        isLoadingFlow,
        resetTimerFlow,
    ) { favouriteAds, advertisementsResult, isLoading, resetTimer ->
        AllAdsUiState(
            isLoading = isLoading,
            resetTimer = resetTimer,
            advertisementsResult = advertisementsResult.map { advertisements ->
                advertisements.map { advertisement ->
                    advertisement.copy(
                        isFavourite = favouriteAds.any { it.id == advertisement.id }
                    )
                }.sortedByDescending { it.score }
            }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AllAdsUiState(
            isLoading = true,
            advertisementsResult = Result.success(emptyList()),
            resetTimer = false
        )
    )

    init {
        viewModelScope.launch {
            loadWithBackoff()
        }
    }

    private suspend fun loadWithBackoff() = withContext(Dispatchers.IO) {
        var attempts = 0
        do {
            val result = loadAdvertisements()

            advertisementsFlow.emit(result)
            isLoadingFlow.value = false

            if (result.isSuccess) {
                break
            }

            attempts++

            // Retry every ten seconds. Ideally, we would have some kind of backoff
            // (exponential?) but we're making it simple here with counting.
            resetTimerFlow.emit(!(resetTimerFlow.value ?: false))
            delay(10L * 1000)
        } while (advertisementsFlow.value.exceptionOrNull() is IOException && attempts < 3)

        resetTimerFlow.emit(null)
    }

    fun refresh() {
        viewModelScope.launch {
            isLoadingFlow.value = true
            advertisementsFlow.value = loadAdvertisements()
            isLoadingFlow.value = false
        }
    }

    private suspend fun loadAdvertisements() = adsRepository.getAds(true)


    fun toggleFavourite(advertisement: Advertisement) {
        viewModelScope.launch {
            adsRepository.toggleFavourite(advertisement)
        }
    }
}