package com.ns.theendcompose.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ns.theendcompose.data.model.SearchQuery

@Dao
interface SearchQueryDao {
    @Query("select 'query' from SearchQuery where 'query' like '%' || :query || '%' order by last_use_date desc limit 3")
    suspend fun searchQueries(query: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuery(searchQuery: SearchQuery)
}