package com.ns.theendcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ns.theendcompose.data.local.db.SearchQueryDao
import com.ns.theendcompose.data.local.db.movie.*
import com.ns.theendcompose.data.local.db.tvshow.*
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
abstract class AppDatabase: RoomDatabase() {

    //Movies
    abstract fun moviesDao(): MoviesDao
    abstract fun favoritesTvShowsDao(): FavoritesTvShowsDao
    abstract fun favoritesMoviesDao(): FavoritesMoviesDao
    abstract fun recentlyBrowsedMovies(): RecentlyBrowsedMoviesDao
    abstract fun moviesRemoteKeysDao(): MoviesRemoteKeysDao
    abstract fun moviesDetailsDao(): MoviesDetailsDao
    abstract fun moviesDetailsRemoteKeys(): MoviesDetailsRemoteKeysDao

    //Tv Shows
    abstract fun tvShowsDao(): TvShowsDao
    abstract fun favoritesTvShowDao(): FavoritesTvShowsDao
    abstract fun recentlyBrowsedTvShows(): RecentlyBrowsedTvShowsDao
    abstract fun tvShowsRemoteKeysDao(): TvShowsRemoteKeysDao
    abstract fun tvShowsDetailsDao(): TvShowsDetailsDao
    abstract fun tvShowsDetailsRemoteKeys(): TvShowsDetailsRemoteKeysDao

    abstract fun searchQueryDao(): SearchQueryDao
}