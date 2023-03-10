package com.ns.theendcompose.data.repository.search

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.SearchQuery
import com.ns.theendcompose.data.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun multiSearch(
        query: String,
        includeAdult: Boolean = false,
        year: Int? = null,
        releaseYear: Int? = null,
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ): Flow<PagingData<SearchResult>>

    suspend fun searchQueries(query: String): List<String>

    fun addSearchQuery(searchQuery: SearchQuery)
}