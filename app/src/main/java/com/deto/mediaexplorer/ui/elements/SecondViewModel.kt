package com.deto.mediaexplorer.ui.elements

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.Element
import com.deto.mediaexplorer.data.ElementRepository
import com.deto.mediaexplorer.data.remote.services.ElementService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

sealed class ElementsUiState {
    data class Success(val elements: List<Element>) : ElementsUiState()
    data class Error(val message: String) : ElementsUiState()
    object Loading : ElementsUiState()
    object Idle : ElementsUiState()
}


/*
class SecondViewModel (private val elementRepository: ElementRepository) : ViewModel() {

    fun getElementsForCategory(categoryId: Int): Flow<List<Element>> {
        return elementRepository.getAllElementsStream(categoryId)
    }

    fun deleteElementById(id: Int) {
        viewModelScope.launch {
            elementRepository.deleteElementById(id)
        }
    }



}*/

class SecondViewModel(
    private val elementService: ElementService
) : ViewModel(){
    var elementsUiState: ElementsUiState by mutableStateOf(ElementsUiState.Loading)
        private set

    fun resetUiState() {
        elementsUiState = ElementsUiState.Idle
    }

    private fun getElementsByCategory(categoryId: Int){
        viewModelScope.launch {
            elementsUiState = ElementsUiState.Loading
            elementsUiState = try {
                val filteredElements = elementService.getElementsByCategory(categoryId)
                ElementsUiState.Success(filteredElements)
            } catch (e: Exception) {
                ElementsUiState.Error(e.message ?: "error")
            }
        }
    }

    fun refreshElements(categoryId: Int) {
        getElementsByCategory(categoryId)
    }

    fun addElement(name: String, description: String, classification: Int, categoryId: Int) {
        viewModelScope.launch {
            elementsUiState = ElementsUiState.Loading
            try {
                elementService.addElement(Element(0, name, description, classification, null, categoryId))
                val updatedList = elementService.getElementsByCategory(categoryId)
                elementsUiState = ElementsUiState.Success(updatedList)
            } catch (e: Exception) {
                elementsUiState = ElementsUiState.Error(e.message ?: "error")
            }
        }
    }
}

