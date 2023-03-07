package com.ns.theendcompose.ui.screens.reviews

import android.os.Parcelable
import com.ns.theendcompose.data.model.MediaType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewsScreenNavArgs(
    val startRoute: String,
    val mediaId: Int,
    val type: MediaType
) : Parcelable