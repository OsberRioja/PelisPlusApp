package com.ucb.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ucb.model.Genre
import com.ucb.model.Movie
import com.ucb.model.MovieGenreCrossRef
import com.ucb.model.MovieWithGenres
import kotlinx.coroutines.flow.Flow

@Dao
interface IMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table LEFT JOIN movie_details_table ON movie_table.movieId = movie_details_table.movieId WHERE movie_details_table.isFavorite = true")
    fun getFavoriteMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table LEFT JOIN movie_details_table ON movie_table.movieId = movie_details_table.movieId WHERE movie_details_table.vote <> 0")
    fun getRatedMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM  genre_table")
    fun getAllGenres(): List<Genre>

    @Query("SELECT genre_table.* FROM UserGenreCrossRef JOIN genre_table ON UserGenreCrossRef.genreId = genre_table.genreId WHERE UserGenreCrossRef.userId = :userId")
    fun getAllGenresProfile(userId: Int): List<Genre>

    @Query("SELECT COUNT(*) FROM movie_table")
    suspend fun getMovieCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<Genre>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenreCrossRef(crossRef: List<MovieGenreCrossRef>)

    @Transaction
    @Query("SELECT * FROM movie_table WHERE movieId = :movieId")
    suspend fun getMovieWithGenres(movieId: Int): List<MovieWithGenres>

    @Transaction
    @Query("SELECT movie_table.* FROM movie_table JOIN MovieGenreCrossRef ON movie_table.movieId = MovieGenreCrossRef.movieId WHERE genreId = :genreId")
    fun getGenreWithMovies(genreId: Int): Flow<List<Movie>?>

    @Query("SELECT * FROM movie_table WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): LiveData<Movie>

    @Transaction
    suspend fun updateVotes(movieId: Int, voteStars: Int, newVote: Double) {
        setVoteStars(voteStars, movieId)
        setNewVote(newVote, movieId)
    }

    @Query("UPDATE movie_details_table SET isFavorite=NOT isFavorite  WHERE movieId = :movieID")
    fun updateFavorite(movieID: Int)

    @Query("UPDATE movie_table SET newVote = :newRating WHERE movieId = :movieID")
    fun setNewVote(newRating: Double, movieID: Int)

    @Query("UPDATE movie_table SET voteSelf = :newRating WHERE movieId = :movieID")
    fun setVoteStars(newRating: Int, movieID: Int)

    @Query("SELECT * FROM movie_table WHERE movieId = :movieId")
    fun getMovieById2(movieId: Int): Movie?
}