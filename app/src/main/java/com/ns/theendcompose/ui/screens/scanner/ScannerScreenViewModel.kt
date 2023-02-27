package com.ns.theendcompose.ui.screens.scanner

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.ns.theendcompose.BaseViewModel
import com.ns.theendcompose.domain.usecase.ScanBitmapForTextUseCaseImpl
import com.ns.theendcompose.utils.Roi
import com.ns.theendcompose.utils.TextRecognitionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerScreenViewModel @Inject constructor(
    private val scanBitmapForTextUseCaseImpl: ScanBitmapForTextUseCaseImpl
) : BaseViewModel() {

    private val scanState: MutableStateFlow<TextRecognitionHelper.ScanState> = MutableStateFlow(
        TextRecognitionHelper.ScanState.Idle
    )

    val uiState: StateFlow<ScannerScreenUIState> = scanState.map { scanState ->
        val isScanInProgress = scanState is TextRecognitionHelper.ScanState.Loading
        val scanResult = when (scanState) {
            is TextRecognitionHelper.ScanState.Success -> ScanResult.Success(scanState.text)
            is TextRecognitionHelper.ScanState.Error -> ScanResult.Error(scanState.message)
            else -> null
        }
        val validationErrorResId = when (scanState) {
            is TextRecognitionHelper.ScanState.InvalidText -> scanState.errorResId
            else -> null
        }
        ScannerScreenUIState(
            scanningInProgress = isScanInProgress,
            scanResult = scanResult,
            validationErrorResId = validationErrorResId
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ScannerScreenUIState.default)

    fun onBitmapCaptured(bitmap: Bitmap, rotation: Float, roi: Roi?) {
        viewModelScope.launch(Dispatchers.IO) {
            scanBitmapForTextUseCaseImpl(bitmap, rotation, roi).collect { state ->
                scanState.emit(state)
            }
        }
    }
}