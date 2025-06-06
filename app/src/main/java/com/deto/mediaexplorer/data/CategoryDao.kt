package com.deto.mediaexplorer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Category)

    @Update
    suspend fun update(item: Category)

    @Query("DELETE FROM categories WHERE id= :id")
    suspend fun deleteCategoryById(id: Int)

    // Consulta todas las categorías con sus elementos relacionados
    @Transaction
    @Query("SELECT * from categories ORDER BY id ASC")
    fun getAllItems(): Flow<List<CategoryWithElements>>

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCount(): Int
}