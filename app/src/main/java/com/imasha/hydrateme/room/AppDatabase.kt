package com.imasha.hydrateme.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User

@Database(entities = [User::class, Record::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun recordDao(): RecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "hydrateme_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
