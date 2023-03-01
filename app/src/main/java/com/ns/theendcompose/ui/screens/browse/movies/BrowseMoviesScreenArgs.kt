package com.ns.theendcompose.ui.screens.browse.movies

import android.os.Parcelable
import com.ns.theendcompose.data.model.movie.MovieType
import kotlinx.parcelize.Parcelize

@Parcelize
data class BrowseMoviesScreenArgs(
    val movieType: MovieType
) : Parcelable