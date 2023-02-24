package com.ns.theendcompose.data.local.db.movie

import androidx.room.*
import com.ns.theendcompose.data.model.movie.MovieDetailsRemoteKey
import com.ns.theendcompose.utils.MovieEntityTypeConverters

@TypeConverters(MovieEntityTypeConverters::class)
@Dao
interface MoviesDetailsRemoteKeysDao {
    @Query("SELECT * FROM MovieDetailsRemoteKey WHERE language=:language LIMIT 1")
    suspend fun getRemoteKey(language: String): MovieDetailsRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: MovieDetailsRemoteKey)

    @Query("DELETE FROM MovieDetailsRemoteKey WHERE language=:language")
    suspend fun deleteKeys(language: String)
}