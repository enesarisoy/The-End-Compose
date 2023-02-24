package com.ns.theendcompose.domain.usecase

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.SearchResult
import com.ns.theendcompose.data.repository.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaMultiSearchUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(
        query: String,
        deviceLanguage: DeviceLanguage
    ): Flow<PagingData<SearchResult>> {
        return searchRepository.multiSearch(query = query, deviceLanguage = deviceLanguage)
    }

}