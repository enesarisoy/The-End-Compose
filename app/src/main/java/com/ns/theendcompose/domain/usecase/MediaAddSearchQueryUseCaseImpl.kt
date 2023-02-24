package com.ns.theendcompose.domain.usecase

import com.ns.theendcompose.data.model.SearchQuery
import com.ns.theendcompose.data.repository.search.SearchRepository
import javax.inject.Inject

class MediaAddSearchQueryUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(searchQuery: SearchQuery) {
        return searchRepository.addSearchQuery(searchQuery)
    }

}