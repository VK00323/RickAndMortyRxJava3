package com.example.rickandmortyrxjava3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmortyrxjava3.pojo.PojoResult

@Database(entities = [PojoResult::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private val LOCK = Any()
        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let {
                    return it
                }
                val instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                        .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun characterDao(): CharacterDao
}