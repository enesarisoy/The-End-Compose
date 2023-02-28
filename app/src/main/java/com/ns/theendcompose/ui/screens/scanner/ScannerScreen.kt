package com.ns.theendcompose.ui.screens.scanner

import android.Manifest
import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.ns.theendcompose.utils.Roi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.launch

@Destination(
    style = ScannerScreenTransitions::class
)
@Composable
fun AnimatedVisibilityScope.ScannerScreen(
    viewModel: ScannerScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String>
) {

    val uiState by viewModel.uiState.collectAsState()
    val onBackClicked: () -> Unit = {
        navigator.navigateUp()
    }

    val onAcceptClicked = {
        val result = uiState.scanResult
        if (result is ScanResult.Success) {
            resultNavigator.navigateBack(result.text)
        }
    }


}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun ScannerScreenContent(
    uiState: ScannerScreenUIState,
    onBackClicked: () -> Unit = {},
    onBitmapCaptured: (Bitmap, Float, Roi?) -> Unit,
    onAcceptClicked: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val onCloseBottomSheetClick: () -> Unit = {
        if (sheetState.isVisible) {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }

    val errorText = uiState.validationErrorResId?.let {
        stringResource(id = it)
    }

    LaunchedEffect(uiState.scanResult) {
        when (uiState.scanResult) {
            is ScanResult.Success -> {
                if (!sheetState.isVisible) {
                    sheetState.show()
                }
            }
            else -> {
                if (sheetState.isVisible) {
                    sheetState.hide()
                }
            }
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = sheetState,
        scrimColor = Color.Green,
        sheetContent = {

        }
    ) {

    }

}