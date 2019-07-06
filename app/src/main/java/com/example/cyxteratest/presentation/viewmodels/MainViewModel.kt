package com.example.cyxteratest.presentation.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.cyxteratest.commons.observeOnMainThreadAndSubscribeOnIo
import com.example.cyxteratest.data.models.User
import com.example.cyxteratest.data.models.UserState
import com.example.cyxteratest.domain.repositories.UsersRepository
import io.reactivex.disposables.CompositeDisposable
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

    fun searchUser(email: String, password: String, owner: LifecycleOwner) {
        usersRepository.searchUser(email, password)
            .observe(owner, Observer { user ->
                if(user == null) {
                    searchUserSubject.onNext(UserState.notFoundUser)
                } else {
                    searchUserSubject.onNext(UserState.foundedUser)
                }
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}