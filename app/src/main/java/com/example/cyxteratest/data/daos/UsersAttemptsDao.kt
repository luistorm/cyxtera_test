package com.example.cyxteratest.data.daos

import androidx.room.Dao
import androidx.room.Insert
import com.example.cyxteratest.data.models.UserAttempt

@Dao
interface UsersAttemptsDao {
    @Insert
    fun insertAttempt(userAttempt: UserAttempt)
}