package com.ns.theendcompose.data.model

import androidx.annotation.StringRes
import com.ns.theendcompose.R

enum class FavouriteType {
    Movie, TvShow;

    @StringRes
    fun getLabelResourceId() = when (this) {
        Movie -> R.string.favourite_type_movie_label
        TvShow -> R.string.favourite_type_tv_show_label
    }
}