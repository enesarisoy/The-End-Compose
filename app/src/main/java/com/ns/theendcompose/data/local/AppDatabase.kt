package com.ns.theendcompose.data.local

import androidx.room.Database
import androidx.room.TypeConverters
import com.ns.theendcompose.data.model.RecentlyBrowsedMovie
import com.ns.theendcompose.data.model.RecentlyBrowsedTvShow
import com.ns.theendcompose.data.model.SearchQuery
import com.ns.theendcompose.data.model.movie.*
import com.ns.theendcompose.data.model.tvshow.*
import com.ns.theendcompose.utils.DateConverters

@Database(
    entities = [
        MovieFavorite::class,
        TvShowFavorite::class,
        RecentlyBrowsedMovie::class,
        RecentlyBrowsedTvShow::class,
        SearchQuery::class,
        MovieEntity::class,
        MoviesRemoteKeys::class,
        TvShowEntity::class,
        TvShowsRemoteKeys::class,
        MovieDetailEntity::class,
        MovieDetailsRemoteKey::class,
        TvShowDetails::class,
        TvShowDetailsRemoteKey::class
    ],
    version = 1
)
@TypeConverters(DateConverters::class)
abstract class AppDatabase {
}