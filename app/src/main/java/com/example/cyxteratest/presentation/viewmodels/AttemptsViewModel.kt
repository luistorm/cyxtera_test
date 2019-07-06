package com.example.cyxteratest.presentation.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.cyxteratest.data.models.UserAttempt
import com.example.cyxteratest.domain.repositories.UsersRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class AttemptsViewModel(private val usersRepository: UsersRepository): ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val getAttemptsSubject: PublishSubject<List<UserAttempt>> = PublishSubject.create()

    fun getAttempts(email: String, owner: LifecycleOwner) {
        usersRepository.getAllAttempts(email)
            .observe(owner, Observer { list ->
                list?.let {
                    getAttemptsSubject.onNext(it)
                }
            })
    }
}