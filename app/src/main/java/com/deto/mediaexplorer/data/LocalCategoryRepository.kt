package com.deto.mediaexplorer.data

import kotlinx.coroutines.flow.Flow

class LocalCategoryRepository(private val itemDao: CategoryDao) : CategoryRepository {

    override fun getAllCategoriesStream(): Flow<List<CategoryWithElements>> = itemDao.getAllItems()

    override suspend fun insertCategory(category: Category) = itemDao.insert(category)

    override suspend fun deleteCategoryById(id: Int)  = itemDao.deleteCategoryById(id)

    override suspend fun updateCategory(category: Category) = itemDao.update(category)

}