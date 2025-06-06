package com.deto.mediaexplorer.data

import com.deto.mediaexplorer.R
import kotlinx.coroutines.flow.Flow

class LocalElementRepository( private val itemDao: ElementDao) : ElementRepository {

    override fun getAllElementsStream(categoryId: Int): Flow<List<Element>> =
        itemDao.getAllItems(categoryId)

    override fun getElementStream(id: Int): Flow<Element> = itemDao.getItem(id)

    override suspend fun insertElement(element: Element) = itemDao.insert(element)

    override suspend fun deleteElementById(id: Int) = itemDao.deleteElementById(id)

    override suspend fun updateElement(element: Element) = itemDao.update(element)


    suspend fun isEmpty(): Boolean {
        return itemDao.getCount() == 0 // Nueva función en el DAO
    }

}