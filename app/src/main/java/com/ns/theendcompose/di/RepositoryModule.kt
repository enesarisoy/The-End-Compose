package com.ns.theendcompose.di

import android.content.Context
import com.ns.theendcompose.data.paging.ConfigDataSource
import com.ns.theendcompose.data.remote.api.movie.TmdbMoviesApiHelper
import com.ns.theendcompose.data.remote.api.others.TmdbOthersApiHelper
import com.ns.theendcompose.data.remote.api.tvshow.TmdbTvShowsApiHelper
import com.ns.theendcompose.data.repository.browsed.RecentlyBrowsedRepository
import com.ns.theendcompose.data.repository.browsed.RecentlyBrowsedRepositoryImpl
import com.ns.theendcompose.data.repository.config.ConfigRepository
import com.ns.theendcompose.data.repository.config.ConfigRepositoryImpl
import com.ns.theendcompose.data.repository.favorites.FavoritesRepository
import com.ns.theendcompose.data.repository.favorites.FavoritesRepositoryImpl
import com.ns.theendcompose.data.repository.movie.MovieRepository
import com.ns.theendcompose.data.repository.movie.MovieRepositoryImpl
import com.ns.theendcompose.data.repository.person.PersonRepository
import com.ns.theendcompose.data.repository.person.PersonRepositoryImpl
import com.ns.theendcompose.data.repository.search.SearchRepository
import com.ns.theendcompose.data.repository.search.SearchRepositoryImpl
import com.ns.theendcompose.data.repository.season.SeasonRepository
import com.ns.theendcompose.data.repository.season.SeasonRepositoryImpl
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import com.ns.theendcompose.data.repository.tvshow.TvShowRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideConfigDataSource(
        @ApplicationContext context: Context,
        externalScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        apiOtherHelper: TmdbOthersApiHelper,
        apiMovieHelper: TmdbMoviesApiHelper,
        apiTvShowHelper: TmdbTvShowsApiHelper
    ): ConfigDataSource = ConfigDataSource(
        context = context,
        externalScope = externalScope,
        defaultDispatcher = dispatcher,
        apiOtherHelper = apiOtherHelper,
        apiMovieHelper = apiMovieHelper,
        apiTvShowHelper = apiTvShowHelper
    )

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryBinds {
        @Binds
        @Singleton
        fun provideConfigRepository(impl: ConfigRepositoryImpl): ConfigRepository

        @Binds
        @Singleton
        fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

        @Binds
        @Singleton
        fun bindTvShowRepository(impl: TvShowRepositoryImpl): TvShowRepository

        @Binds
        @Singleton
        fun bindsBrowsedRepository(impl: RecentlyBrowsedRepositoryImpl): RecentlyBrowsedRepository

        @Binds
        @Singleton
        fun bindFavouritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository

        @Binds
        @Singleton
        fun bindPersonRepository(impl: PersonRepositoryImpl): PersonRepository

        @Binds
        @Singleton
        fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

        @Binds
        @Singleton
        fun bindSeasonRepository(impl: SeasonRepositoryImpl): SeasonRepository
    }
}