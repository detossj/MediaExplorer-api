package com.deto.mediaexplorer.ui.elements

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.Element
import com.deto.mediaexplorer.data.remote.services.ElementService
import com.deto.mediaexplorer.ui.categories.CategoriesUiState
import kotlinx.coroutines.launch

sealed class ElementsUiState {
    data class Success(val elements: List<Element>) : ElementsUiState()
    data class Error(val message: String) : ElementsUiState()
    object Loading : ElementsUiState()
    object Idle : ElementsUiState()
}


class SecondViewModel(
    private val elementService: ElementService
) : ViewModel(){
    var elementsUiState: ElementsUiState by mutableStateOf(ElementsUiState.Loading)
        private set

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

    fun deleteElementById(id: Int, categoryId: Int) {
        viewModelScope.launch {
            elementsUiState = ElementsUiState.Loading
            try {
                elementService.deleteElementById(id)
                val updatedList = elementService.getElementsByCategory(categoryId)
                elementsUiState = ElementsUiState.Success(updatedList)
            } catch (e: Exception) {
                elementsUiState = ElementsUiState.Error(e.message ?: "error")
            }
        }
    }
}

