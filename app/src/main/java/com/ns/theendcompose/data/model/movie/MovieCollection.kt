package com.ns.theendcompose.data.model.movie

import com.ns.theendcompose.data.model.Part

data class MovieCollection(
    val name: String,
    val parts: List<Part>
)