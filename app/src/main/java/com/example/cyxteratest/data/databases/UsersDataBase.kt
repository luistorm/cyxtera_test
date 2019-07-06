package com.example.cyxteratest.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cyxteratest.data.models.User
import com.example.cyxteratest.data.models.UserAttempt
import com.example.cyxteratest.data.daos.UserDao
import com.example.cyxteratest.data.daos.UsersAttemptsDao

@Database(entities = arrayOf(User::class, UserAttempt::class), version = 1)
abstract class UsersDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun attemptsDao(): UsersAttemptsDao

    companion object {
        @Volatile
        private var INSTANCE: UsersDataBase? = null

        fun getDatabase(context: Context): UsersDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDataBase::class.java,
                    "users_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}