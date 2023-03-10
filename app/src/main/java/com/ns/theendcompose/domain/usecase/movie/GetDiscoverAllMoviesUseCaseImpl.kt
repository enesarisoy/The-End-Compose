package com.ns.theendcompose.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.map
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.Presentable
import com.ns.theendcompose.data.repository.movie.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetDiscoverAllMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(deviceLanguage: DeviceLanguage): Flow<PagingData<Presentable>> {
        return movieRepository.discoverMovies(deviceLanguage)
            .mapLatest { data -> data.map { it } }
    }
}