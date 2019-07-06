package com.example.cyxteratest.domain.repositories

import androidx.lifecycle.LiveData
import com.example.cyxteratest.data.daos.UserDao
import com.example.cyxteratest.data.daos.UsersAttemptsDao
import com.example.cyxteratest.data.models.User
import com.example.cyxteratest.data.models.UserAttempt
import io.reactivex.Completable

class UsersRepository(private val userDao: UserDao, private val usersAttemptsDao: UsersAttemptsDao) {

    val allUsers: LiveData<List<User>> = userDao.getUsers()

    fun insertUser(user: User): Completable {
        return Completable.fromCallable {
            userDao.insertUser(user)
        }
    }

    fun searchUser(email: String, password: String): LiveData<User> = userDao.searchUser(email, password)

    fun insertAttempt(userAttempt: UserAttempt): Completable {
        return Completable.fromCallable {
            usersAttemptsDao.insertAttempt(userAttempt)
        }
    }
}