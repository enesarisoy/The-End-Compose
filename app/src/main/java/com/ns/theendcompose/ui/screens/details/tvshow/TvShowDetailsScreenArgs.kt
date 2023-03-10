package com.ns.theendcompose.ui.screens.details.tvshow

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowDetailsScreenArgs(
    val tvShowId: Int,
    val startRoute: String
): Parcelable