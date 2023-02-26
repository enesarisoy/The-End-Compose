package com.ns.theendcompose.utils

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.palette.graphics.Palette

fun Bitmap.isDark(): Boolean {
    return Palette.from(this).generate().dominantSwatch?.run {
        getPerceptiveLuminance() < 0.5
    } ?: true
}

fun Palette.Swatch.getPerceptiveLuminance(): Float {
    val r : Int = Color.red(rgb)
    val g : Int = Color.green(rgb)
    val b : Int = Color.blue(rgb)

    return ((0.299f * r) + (0.587f * g) + (0.114f * b)) / 255f
}

fun ColorFilter.Companion.grayScale(): ColorFilter {
    val grayScaleMatrix = ColorMatrix(
        floatArrayOf(
            0.33f, 0.33f, 0.33f, 0f, 0f,
            0.33f, 0.33f, 0.33f, 0f, 0f,
            0.33f, 0.33f, 0.33f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )
    )

    return colorMatrix(grayScaleMatrix)
}