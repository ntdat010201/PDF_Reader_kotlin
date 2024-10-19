package com.example.pdfreader_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.utlis.FileDAO


@Database(entities = [ModelFileItem::class], version = 1)
abstract class FileDatabase : RoomDatabase() {
    abstract fun fileDao(): FileDAO

    companion object {
        @Volatile
        private var INSTANCE: FileDatabase? = null

        fun getDatabase(context: Context): FileDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FileDatabase::class.java,
                    "file_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}