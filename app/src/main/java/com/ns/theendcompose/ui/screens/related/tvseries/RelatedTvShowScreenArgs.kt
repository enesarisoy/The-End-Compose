package com.ns.theendcompose.ui.screens.related.tvseries

import android.os.Parcelable
import com.ns.theendcompose.data.model.RelationType
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelatedTvShowScreenArgs(
    val tvShowId: Int,
    val type: RelationType,
    val startRoute: String
) : Parcelable