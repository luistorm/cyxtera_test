package com.example.cyxteratest.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cyxteratest.data.models.UserAttempt

@Dao
interface UsersAttemptsDao {
    @Insert
    fun insertAttempt(userAttempt: UserAttempt)

    @Query("SELECT * FROM users_attempts WHERE user_email=:email")
    fun getAttemptsByUser(email: String): LiveData<List<UserAttempt>>
}