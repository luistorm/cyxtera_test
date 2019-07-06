package com.example.cyxteratest.presentation.di

import com.example.cyxteratest.data.databases.UsersDataBase
import com.example.cyxteratest.data.network.UserApi
import com.example.cyxteratest.domain.repositories.UsersRepository
import com.example.cyxteratest.presentation.CyxteraTestApplication
import com.example.cyxteratest.presentation.viewmodels.AttemptsViewModel
import com.example.cyxteratest.presentation.viewmodels.MainViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val daosModule: Module = module {
    factory { UsersDataBase.getDatabase(CyxteraTestApplication.appContext).userDao() }
    factory { UsersDataBase.getDatabase(CyxteraTestApplication.appContext).attemptsDao() }
}

val repositoriesModule: Module = module {
    factory { UsersRepository(get(), get(), userApi) }
}

val viewModelsModule: Module = module {
    viewModel { MainViewModel(get()) }
    viewModel { AttemptsViewModel(get()) }
}

private val retrofit = Retrofit.Builder()
    .baseUrl("http://api.geonames.org")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val userApi: UserApi = retrofit.create(UserApi::class.java)