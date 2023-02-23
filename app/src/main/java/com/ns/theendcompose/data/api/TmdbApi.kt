package com.ns.theendcompose.data.api

import androidx.annotation.FloatRange
import com.ns.theendcompose.data.model.*
import com.ns.theendcompose.data.model.movie.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("configuration")
    fun getConfig(): Call<Config>


}