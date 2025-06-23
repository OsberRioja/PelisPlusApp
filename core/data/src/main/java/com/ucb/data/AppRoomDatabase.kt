package com.ucb.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ucb.model.Genre
import com.ucb.model.Movie
import com.ucb.model.MovieComment
import com.ucb.model.MovieDetails
import com.ucb.model.MovieGenreCrossRef
import com.ucb.model.User
import com.ucb.model.UserGenreCrossRef
import com.ucb.model.UserState

@Database(entities = [
    User::class, Movie::class,
    Genre::class, MovieGenreCrossRef::class,
    UserGenreCrossRef::class,
    UserState::class,MovieComment::class,
    MovieDetails::class], version = 5)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun userDao(): IUserDao
    abstract fun movieDao(): IMovieDao
    abstract fun stateDao(): UserStateDao
    abstract fun movieCommentDao(): IMovieCommentDao
    abstract fun movieDetailsDao(): IMovieDetailsDao


    companion object {
        @Volatile
        private var Instance: AppRoomDatabase? = null
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
            CREATE TABLE user_table_new (
                userId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                username TEXT NOT NULL,
                birthDate TEXT NOT NULL,
                email TEXT NOT NULL,
                password TEXT NOT NULL
            )
        """.trimIndent())

                // Copiar los datos de la tabla anterior a la nueva
                database.execSQL("""
            INSERT INTO user_table_new (userId, username, birthDate, email, password)
            SELECT id, username, birthDate, email, password FROM user_table
        """.trimIndent())

                // Eliminar la tabla anterior
                database.execSQL("DROP TABLE user_table")

                // Renombrar la nueva tabla al nombre original
                database.execSQL("ALTER TABLE user_table_new RENAME TO user_table")

                // Crear la nueva tabla UserGenreCrossRef
                database.execSQL("""
            CREATE TABLE UserGenreCrossRef (
                genreId INTEGER NOT NULL,
                userId INTEGER NOT NULL,
                PRIMARY KEY (userId, genreId)
            )
        """.trimIndent())
            }
        }

        val MIGRATION_1_3 = object : Migration(1, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE user_state (
                        id INTEGER PRIMARY KEY NOT NULL,
                        isLoggedIn BOOLEAN NULL
                    )
                    """.trimIndent())
            }
        }

        val MIGRATION_1_4 = object : Migration(1,4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE movie_table ADD COLUMN releaseDate TEXT NOT NULL")
                database.execSQL("ALTER TABLE movie_table ADD COLUMN voteSelf INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE movie_table ADD COLUMN newVote REAL NOT NULL DEFAULT 0.0")
            }
        }
        val MIGRATION_1_5 = object : Migration(1, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Crear la tabla de comentarios
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS movie_comments (
                        commentId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        movieId INTEGER NOT NULL,
                        comment TEXT NOT NULL,
                        FOREIGN KEY (movieId) REFERENCES MovieDetails(movieId) ON DELETE CASCADE
                    )
                """.trimIndent())

                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS movie_details_table (
                        movieId INTEGER PRIMARY KEY NOT NULL,
                        vote INTEGER NOT NULL,
                        isFavorite BOOLEAN NULL
                    )
                """.trimIndent())
            }
        }

        fun getDatabase(context: Context): AppRoomDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "item_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_1_3)
                    .addMigrations(MIGRATION_1_4)
                    .addMigrations(MIGRATION_1_5)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}