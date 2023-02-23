package com.ns.theendcompose.data.model

import com.ns.theendcompose.utils.formatted
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class DateParam(
    private val date: Date
){
    override fun toString(): String {
        return date.formatted("yyyy-MM-dd")
    }
}
