package com.ns.theendcompose.ui.screens.browse.tvshows

import android.os.Parcelable
import com.ns.theendcompose.data.model.tvshow.TvShowType
import kotlinx.parcelize.Parcelize

@Parcelize
data class BrowseTvShowsScreenArgs(
    val tvShowType: TvShowType
) : Parcelable