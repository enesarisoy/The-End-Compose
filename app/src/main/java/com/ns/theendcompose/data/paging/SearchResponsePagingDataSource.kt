package com.ns.theendcompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.MediaType
import com.ns.theendcompose.data.model.SearchResult
import com.ns.theendcompose.data.remote.api.others.TmdbOthersApiHelper
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

class SearchResponsePagingDataSource(
    private val apiOtherHelper: TmdbOthersApiHelper,
    private val query: String,
    private val includeAdult: Boolean,
    private val year: Int? = null,
    private val releaseYear: Int? = null,
    private val language: String = DeviceLanguage.default.languageCode,
    private val region: String = DeviceLanguage.default.region
) : PagingSource<Int, SearchResult>() {
    override fun getRefreshKey(state: PagingState<Int, SearchResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResult> {
        return try {
            val nextPage = params.key ?: 1
            val searchResponse = apiOtherHelper.multiSearch(
                page = nextPage,
                isoCode = language,
                region = region,
                query = query,
                includeAdult = includeAdult,
                year = year,
                releaseYear = releaseYear
            )
            val currentPage = searchResponse.page
            val totalPages = searchResponse.totalPages

            LoadResult.Page(
                data = searchResponse.results.filter { result ->
                    result.mediaType in setOf(
                        MediaType.Movie,
                        MediaType.Tv
                    )
                },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (currentPage + 1 > totalPages) null else currentPage + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: JsonDataException) {
            FirebaseCrashlytics.getInstance().recordException(e)
            LoadResult.Error(e)
        }
    }
}