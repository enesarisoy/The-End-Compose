package com.ns.theendcompose.data.local.db.tvshow

import androidx.paging.PagingSource
import androidx.room.*
import com.ns.theendcompose.data.model.tvshow.TvShowEntity
import com.ns.theendcompose.data.model.tvshow.TvShowEntityType
import com.ns.theendcompose.utils.TvShowEntityTypeConverters

@TypeConverters(TvShowEntityTypeConverters::class)
@Dao
interface TvShowsDao {
    @Query("select * from TvShowEntity where type=:type and language=:language")
    fun getAllTvShows(type: TvShowEntityType, language: String): PagingSource<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTvShow(movies: List<TvShowEntity>)

    @Query("DELETE FROM TvShowEntity WHERE type=:type AND language=:language")
    suspend fun deleteTvShowsOfType(type: TvShowEntityType, language: String)
}