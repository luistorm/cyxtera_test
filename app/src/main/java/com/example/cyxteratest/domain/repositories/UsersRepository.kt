package com.example.cyxteratest.domain.repositories

import androidx.lifecycle.LiveData
import com.example.cyxteratest.data.daos.UserDao
import com.example.cyxteratest.data.models.User
import io.reactivex.Completable

class UsersRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getUsers()

    fun insertUser(user: User): Completable {
        return Completable.fromCallable {
            userDao.insertUser(user)
        }
    }


}