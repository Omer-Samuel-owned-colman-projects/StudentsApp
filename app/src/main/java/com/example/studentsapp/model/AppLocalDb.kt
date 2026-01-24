package com.example.studentsapp.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Student::class], version = 5)
abstract class AppLocalDbRepository : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}

object AppLocalDb {
    var db: AppLocalDbRepository? = null
        private set

    fun init(context: Context) {
        if (db == null) {
            db = Room.databaseBuilder(
                context.applicationContext,
                AppLocalDbRepository::class.java,
                "database.db"
            )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
            db?.openHelper?.writableDatabase
        }
    }
}
