package com.ns.theendcompose.domain.usecase

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.MediaType
import com.ns.theendcompose.data.model.Review
import com.ns.theendcompose.data.repository.movie.MovieRepository
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetMediaTypeReviewsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) {
    operator fun invoke(mediaId: Int, type: MediaType): Flow<PagingData<Review>> {
        return when (type) {
            MediaType.Movie -> movieRepository.movieReviews(mediaId)
            MediaType.Tv -> tvShowRepository.tvShowReviews(mediaId)
            else -> emptyFlow()
        }
    }
}