package com.example.adsapplication.view.compose.ui.screen.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adsapplication.domain.model.Advertisement
import com.example.adsapplication.domain.repository.AdsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AllAdsViewModel @Inject constructor(
    private val adsRepository: AdsRepository,
) : ViewModel() {
    val isLoadingFlow = MutableStateFlow(false)
    val advertisementFlow = MutableStateFlow(Result.success(emptyList<Advertisement>()))

    init {
        viewModelScope.launch {
            isLoadingFlow.value = true
            loadWithBackoff()
        }
    }

    private suspend fun loadWithBackoff() = withContext(Dispatchers.IO) {
        var attempts = 0
        do {
            val result = loadAdvertisements()

            advertisementFlow.emit(result)
            isLoadingFlow.value = false

            if (result.isSuccess) {
                break
            }

            attempts++

            // Retry every ten seconds. Ideally, we would have some kind of backoff
            // (exponential?) but we're making it simple here with counting.
            delay(10L * 1000)
        } while (advertisementFlow.value.exceptionOrNull() is IOException && attempts < 3)
    }

    fun refresh() {
        viewModelScope.launch {
            advertisementFlow.emit(loadAdvertisements())
        }
    }

    private suspend fun loadAdvertisements() = adsRepository.getAds(true)


}