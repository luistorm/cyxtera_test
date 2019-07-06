package com.example.cyxteratest.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "users_attempts",
    foreignKeys = [ForeignKey(entity = User::class,
        parentColumns = arrayOf("email"),
        childColumns = arrayOf("user_email"))])
data class UserAttempt (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "attempt_id")
    val attemptId: Int = 0,
    @ColumnInfo(name = "user_email")
    val userEmail: String,
    @ColumnInfo(name = "type")
    val type: Boolean,
    @ColumnInfo(name = "attempt_date")
    val date: String
)