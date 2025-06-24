package com.ucb.repository

import android.content.Context
import com.ucb.data.AppRoomDatabase
import com.ucb.model.User
import com.ucb.model.UserGenreCrossRef
import com.ucb.model.UserState

class UserRepository(private val context: Context) {
    private val userDao = AppRoomDatabase.getDatabase(context).userDao()
    private val stateDao = AppRoomDatabase.getDatabase(context).stateDao()


    suspend fun insert(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return (userDao.loginUser2(email, password) > 0);
    }

    suspend fun getGenreIdByName(genreName: String): Int {
        val formattedGenreName = genreName.map {
                char ->
            when(char) {
                'ó' -> 'o'
                'á' -> 'a'
                'é' -> 'e'
                'í' -> 'i'
                'ú' -> 'u'
                else -> char
            }
        }.joinToString("")
        return userDao.getGenreIdByName(formattedGenreName)
    }

    suspend fun insertUserGenreCrossRef(userGenreCrossRef: UserGenreCrossRef) {
        return userDao.insertUserGenreCrossRef(userGenreCrossRef)
    }

    suspend fun saveLoginState(userIdCurrent: Int, isLoggedIn: Boolean) {
        stateDao.deleteAll()
        stateDao.setUserState(UserState(id = userIdCurrent, isLoggedIn = isLoggedIn))
    }

    suspend fun getLoginState(): Int {
        return stateDao.getUserState()
    }

    suspend fun getIdByUsername(name: String): Int {
        return userDao.getIdByUsername(name)
    }

    fun getInfoUser(): User {
        return stateDao.getInfoUser()
    }
}

