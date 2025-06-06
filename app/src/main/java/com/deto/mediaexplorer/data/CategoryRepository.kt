package com.deto.mediaexplorer.data

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategoriesStream(): Flow<List<CategoryWithElements>>

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategoryById(id: Int)

    suspend fun updateCategory(category: Category)

}