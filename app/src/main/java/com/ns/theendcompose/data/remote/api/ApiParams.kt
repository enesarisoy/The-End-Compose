package com.ns.theendcompose.data.remote.api

import org.joda.time.Duration

object ApiParams {

    const val baseUrl = "https://api.themoviedb.org/3/"

    const val cacheSize = (10 * 1024 * 1024).toLong()

    object Timeouts {
        val connect = Duration.standardSeconds(10)
        val write = Duration.standardSeconds(10)
        val read = Duration.standardSeconds(10)
    }

}