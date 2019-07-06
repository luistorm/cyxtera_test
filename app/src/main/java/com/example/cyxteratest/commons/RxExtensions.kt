package com.example.cyxteratest.commons

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Completable.observeOnMainThreadAndSubscribeOnIo(): Completable = this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())