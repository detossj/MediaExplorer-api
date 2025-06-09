package com.deto.mediaexplorer.ui.elements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.Element
import com.deto.mediaexplorer.data.ElementRepository
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