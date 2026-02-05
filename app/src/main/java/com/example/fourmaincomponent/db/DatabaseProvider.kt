package com.example.fourmaincomponent.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var appDatabase: AppDatabase? = null

    private fun getDatabase(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "contacts.db"
            ).build()
        }
        return appDatabase!!
    }

    fun getContactDao(context: Context): ContactDao {
        return getDatabase(context).contactDao()
    }
}
