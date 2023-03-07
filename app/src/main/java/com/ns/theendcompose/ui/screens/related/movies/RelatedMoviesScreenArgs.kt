package com.ns.theendcompose.ui.screens.related.movies

import android.os.Parcelable
import com.ns.theendcompose.data.model.RelationType
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelatedMoviesScreenArgs(
    val movieId: Int,
    val type: RelationType,
    val startRoute: String
) : Parcelable