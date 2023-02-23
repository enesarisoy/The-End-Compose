package com.ns.theendcompose.data.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val language: String,
    val type: MovieEntityType,
    val nextPage: Int?,
    val lastUpdated: Long
)