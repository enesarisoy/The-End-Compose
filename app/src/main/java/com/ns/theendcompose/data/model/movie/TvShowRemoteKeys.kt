package com.ns.theendcompose.data.model.movie

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["language", "type"])])
data class TvShowRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val language: String,
    val type: TvSeriesEntityType,
    val nextPage: Int?,
    val lastUpdated: Long
)