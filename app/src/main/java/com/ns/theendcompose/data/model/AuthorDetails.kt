package com.ns.theendcompose.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorDetails(
    val name: String,
    val username: String,
    @Json(name = "profile_path")
    val profilePath: String?,
    val rating: Float?,
    @Transient
    val avatarUrl: String?
)