package com.example.cyxteratest

import com.example.cyxteratest.data.daos.UserDao
import com.example.cyxteratest.data.daos.UsersAttemptsDao
import com.example.cyxteratest.data.models.TimeZoneResponse
import com.example.cyxteratest.data.models.User
import com.example.cyxteratest.data.network.UserApi
import com.example.cyxteratest.domain.repositories.UsersRepository
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserRepositoryTest {

    private lateinit var usersRepository: UsersRepository

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var usersAttemptsDao: UsersAttemptsDao

    @Mock
    private lateinit var userApi: UserApi

    @Mock
    private lateinit var timeZoneObservable: Observable<TimeZoneResponse>

    @Before
    fun initTestVariables() {
        MockitoAnnotations.initMocks(this)
        usersRepository = UsersRepository(userDao, usersAttemptsDao, userApi)
    }

    @Test
    fun getTimeZoneTest() {
        Mockito.`when`(userApi.getTimeZoneInfo(12.5, 20.5))
            .thenReturn(timeZoneObservable)

        usersRepository.getTimeZoneInfo(12.5, 20.5)

        Mockito.verify(userApi).getTimeZoneInfo(12.5, 20.5)
        Mockito.verifyNoMoreInteractions(userApi)
        Mockito.verifyZeroInteractions(timeZoneObservable)
    }

    @Test
    fun insertUserTest() {
        userDao.insertUser(User("a@a.com", "12345"))

        Mockito.verify(userDao).insertUser(User("a@a.com", "12345"))
        Mockito.verifyNoMoreInteractions(userDao)
        Mockito.verifyZeroInteractions(userApi)
    }

}