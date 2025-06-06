package com.deto.mediaexplorer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ElementDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Element)

    @Update
    suspend fun update(item: Element)

    @Query("DELETE FROM elements WHERE id = :id")
    suspend fun deleteElementById(id: Int)

    @Query("SELECT * from elements WHERE id = :id")
    fun getItem(id: Int): Flow<Element>

    @Query("SELECT * from elements WHERE categoryId = :categoryId")
    fun getAllItems(categoryId: Int): Flow<List<Element>>

    @Query("SELECT COUNT(*) FROM elements")
    suspend fun getCount(): Int
}