package com.ns.theendcompose.data.local.db.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.ns.theendcompose.data.model.movie.MovieEntity
import com.ns.theendcompose.data.model.movie.MovieEntityType
import com.ns.theendcompose.utils.MovieEntityTypeConverters

@TypeConverters(MovieEntityTypeConverters::class)
@Dao
interface MoviesDao {

    @Query("select * from MovieEntity where type=:type and language=:language")
    fun getAllMovies(type: MovieEntityType, language: String): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovies(movies: List<MovieEntity>)

    @Query("delete from MovieEntity where type=:type and language=:language")
    suspend fun deleteMoviesOfType(type: MovieEntityType, language: String)
}