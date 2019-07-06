package com.example.cyxteratest.presentation.di

import com.example.cyxteratest.data.databases.UsersDataBase
import com.example.cyxteratest.domain.repositories.UsersRepository
import com.example.cyxteratest.presentation.CyxteraTestApplication
import com.example.cyxteratest.presentation.viewmodels.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val daosModule: Module = module {
    factory { UsersDataBase.getDatabase(CyxteraTestApplication.appContext).userDao() }
}

val repositoriesModule: Module = module {
    factory { UsersRepository(get()) }
}

val viewModelsModule: Module = module {
    viewModel { MainViewModel(get()) }
}