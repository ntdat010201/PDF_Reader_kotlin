package com.example.pdfreader_kotlin.utlis

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pdfreader_kotlin.model.ModelFileItem

@Dao
interface FileDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteFile(file: ModelFileItem)

    @Delete
    suspend fun removeFavoriteFile(file: ModelFileItem)

    @Query("SELECT * FROM favorite_files")
    fun getAllFavoriteFiles(): LiveData<List<ModelFileItem>>

    @Query("SELECT * FROM favorite_files WHERE path = :filePath LIMIT 1")
    suspend fun isFileFavorite(filePath: String): ModelFileItem?
}