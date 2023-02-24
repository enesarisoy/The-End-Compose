package com.ns.theendcompose.domain.usecase.movie

import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.Video
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.movie.MovieRepository
import javax.inject.Inject


class GetMovieVideosUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguage
    ): ApiResponse<List<Video>> {
        val response = movieRepository.getMovieVideos(
            movieId = movieId,
            isoCode = deviceLanguage.languageCode
        ).awaitApiResponse()

        return when (response) {
            is ApiResponse.Success -> {
                val results = response.data?.result
                val videos = results?.sortedWith(
                    compareBy<Video> { video ->
                        video.language == deviceLanguage.languageCode
                    }.thenByDescending { video ->
                        video.publishedAt
                    }
                )
                ApiResponse.Success(videos)
            }
            is ApiResponse.Failure -> ApiResponse.Failure(response.apiError)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
        }

    }

}