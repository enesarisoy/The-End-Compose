package com.ns.theendcompose.domain.usecase.movie

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.CrewMember
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.movie.Movie
import com.ns.theendcompose.data.repository.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOtherDirectorMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(
        mainDirector: CrewMember,
        deviceLanguage: DeviceLanguage
    ): Flow<PagingData<Movie>> {
        return movieRepository.moviesOfDirector(
            directorId = mainDirector.id,
            deviceLanguage = deviceLanguage
        )
    }
}