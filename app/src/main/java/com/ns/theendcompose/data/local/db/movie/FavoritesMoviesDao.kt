package com.ns.theendcompose.data.local.db.movie

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ns.theendcompose.data.model.movie.MovieFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesMoviesDao {
    @Query("select * from MovieFavorite order by added_date desc")
    fun getAllFavoriteMovies(): DataSource.Factory<Int, MovieFavorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun likeMovie(vararg movieDetails: MovieFavorite)

    @Query("delete from MovieFavorite where id = :movieId")
    suspend fun unlikeMovie(movieId: Int)

    @Query("select id from MovieFavorite")
    fun favoriteMoviesIds(): Flow<List<Int>>

    @Query("select count(id) from MovieFavorite")
    fun favoriteMoviesCount(): Flow<Int>
}