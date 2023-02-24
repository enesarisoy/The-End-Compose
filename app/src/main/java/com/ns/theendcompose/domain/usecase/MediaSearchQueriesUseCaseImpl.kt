package com.ns.theendcompose.domain.usecase

import com.ns.theendcompose.data.repository.search.SearchRepository
import javax.inject.Inject

class MediaSearchQueriesUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<String> {
        return searchRepository.searchQueries(query)
    }
}