package com.deto.mediaexplorer.data

import kotlinx.coroutines.flow.Flow

interface ElementRepository {

    fun getAllElementsStream(categoryId: Int): Flow<List<Element>>

    fun getElementStream(id: Int): Flow<Element>

    suspend fun insertElement(element: Element)

    suspend fun deleteElementById(id: Int)

    suspend fun updateElement(element: Element)
}