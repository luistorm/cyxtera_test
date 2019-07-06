package com.example.cyxteratest.presentation.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.cyxteratest.commons.observeOnMainThreadAndSubscribeOnIo
import com.example.cyxteratest.data.models.User
import com.example.cyxteratest.data.models.UserAttempt
import com.example.cyxteratest.data.models.UserState
import com.example.cyxteratest.domain.repositories.UsersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject



class MainViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val searchUserSubject: PublishSubject<UserState> = PublishSubject.create()

    fun insertUser(email: String, password: String) {
        compositeDisposable.add(
            usersRepository.insertUser(User(email, password))
                .observeOnMainThreadAndSubscribeOnIo()
                .doOnComplete {
                    searchUserSubject.onNext(UserState.insertedUser)
                }
                .subscribe({}, Throwable::printStackTrace)
        )
    }

    fun insertAttempt(deviceLatitude: Double, deviceLongitude: Double, email: String, attemptValue: Boolean) {
        compositeDisposable.add(
            usersRepository.getTimeZoneInfo(deviceLatitude, deviceLongitude)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({
                    saveAttemptDataInDataBase(email, it.time, attemptValue)
                }, Throwable::printStackTrace)
        )
    }

    private fun saveAttemptDataInDataBase(email: String, date: String, attemptValue: Boolean) {
        compositeDisposable.add(
            usersRepository.insertAttempt(UserAttempt(userEmail = email, type = attemptValue, date = date))
                .observeOnMainThreadAndSubscribeOnIo()
                .doOnComplete {
                    if(attemptValue) {
                        searchUserSubject.onNext(UserState.insertedAttempt)
                    } else {
                        searchUserSubject.onNext(UserState.badCredentials)
                    }
                }
                .subscribe({ }, Throwable::printStackTrace)
        )
    }

    fun searchUser(email: String, password: String, owner: LifecycleOwner) {
        usersRepository.searchUser(email)
            .observe(owner, Observer { user ->
                if(user == null) {
                    searchUserSubject.onNext(UserState.notFoundUser)
                } else {
                    if(password== user.password) {
                        searchUserSubject.onNext(UserState.foundedUser)
                    } else {
                        searchUserSubject.onNext(UserState.loginErrorUser)
                    }
                }
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}