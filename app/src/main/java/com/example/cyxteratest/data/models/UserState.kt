package com.example.cyxteratest.data.models

sealed class UserState {
    object foundedUser: UserState()
    object notFoundUser : UserState()
    object insertedUser : UserState()
}