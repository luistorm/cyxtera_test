package com.example.cyxteratest.domain.repositories

import androidx.lifecycle.LiveData
import com.example.cyxteratest.data.daos.UserDao
import com.example.cyxteratest.data.daos.UsersAttemptsDao
import com.example.cyxteratest.data.models.TimeZoneResponse
import com.example.cyxteratest.data.models.User
import com.example.cyxteratest.data.models.UserAttempt
import com.example.cyxteratest.data.network.UserApi
import io.reactivex.Completable
import io.reactivex.Observable

class UsersRepository(private val userDao: UserDao, private val usersAttemptsDao: UsersAttemptsDao, private val userApi: UserApi) {

    fun getAllAttempts(email: String): LiveData<List<UserAttempt>> = usersAttemptsDao.getAttemptsByUser(email)

    fun insertUser(user: User): Completable {
        return Completable.fromCallable {
            userDao.insertUser(user)
        }
    }

    fun searchUser(email: String): LiveData<User> = userDao.searchUser(email)

    fun insertAttempt(userAttempt: UserAttempt): Completable {
        return Completable.fromCallable {
            usersAttemptsDao.insertAttempt(userAttempt)
        }
    }

    fun getTimeZoneInfo(deviceLatitude: Double, deviceLongitude: Double): Observable<TimeZoneResponse> {
        return userApi.getTimeZoneInfo(deviceLatitude, deviceLongitude)
    }
}