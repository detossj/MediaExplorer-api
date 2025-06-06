package com.deto.mediaexplorer.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.Element
import com.deto.mediaexplorer.data.ElementRepository
import com.deto.mediaexplorer.data.LocalCategoryRepository
import com.deto.mediaexplorer.data.LocalElementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SecondViewModel (private val elementRepository: ElementRepository) : ViewModel() {

    fun getElementsForCategory(categoryId: Int): Flow<List<Element>> {
        return elementRepository.getAllElementsStream(categoryId)
    }

    fun deleteElementById(id: Int) {
        viewModelScope.launch {
            elementRepository.deleteElementById(id)
        }
    }



}