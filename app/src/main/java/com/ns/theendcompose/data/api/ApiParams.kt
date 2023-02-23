package com.ns.theendcompose.data.api

import kotlin.time.Duration.Companion.seconds

object ApiParams {

    const val baseUrl = "https://api.themoviedb.org/3/"

    const val cacheSize = (10 * 1024 * 1024).toLong()

    object Timeouts {
        val connect = 10.seconds
        val write = 10.seconds
        val read = 10.seconds
    }
}