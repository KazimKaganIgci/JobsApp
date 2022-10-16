package com.kazim.jobsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteJob::class], version = 1)
abstract class FavoriteJobDatabase : RoomDatabase() {

    abstract fun getRemoteJobDao(): FavoriteJobDAO

    companion object {
        @Volatile
        private var instance: FavoriteJobDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavoriteJobDatabase::class.java,
                "remoteJob_db"
            ).build()
    }
}