package com.ns.theendcompose.domain.usecase

import android.graphics.Bitmap
import com.ns.theendcompose.utils.Roi
import com.ns.theendcompose.utils.TextRecognitionHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScanBitmapForTextUseCaseImpl @Inject constructor(
    private val textRecognitionHelper: TextRecognitionHelper
) {
    operator fun invoke(
        bitmap: Bitmap,
        rotation: Float,
        roi: Roi?
    ): Flow<TextRecognitionHelper.ScanState> {
        return textRecognitionHelper.scanTextFromBitmap(bitmap, rotation, roi)
    }
}