package com.example.cyxteratest.data.models

sealed class UserState {
    object foundedUser: UserState()
    object notFoundUser : UserState()
    object loginErrorUser : UserState()
    object insertedUser : UserState()
    object insertedAttempt : UserState()
    object badCredentials : UserState()
}