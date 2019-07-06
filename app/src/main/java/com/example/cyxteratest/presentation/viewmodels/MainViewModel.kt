package com.example.cyxteratest.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.cyxteratest.commons.observeOnMainThreadAndSubscribeOnIo
import com.example.cyxteratest.data.models.User
import com.example.cyxteratest.domain.repositories.UsersRepository
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun insertUser(email: String, password: String) {
        compositeDisposable.add(
            usersRepository.insertUser(User(email, password))
                .observeOnMainThreadAndSubscribeOnIo()
                .doOnComplete {

                }
                .subscribe({}, Throwable::printStackTrace)
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}