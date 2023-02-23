package com.ns.theendcompose.data.model.movie

import com.ns.theendcompose.data.model.tvshow.TvShow
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowResponse(
    val page: Int,
    @Json(name = "results")
    val tvSeries: List<TvShow>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)