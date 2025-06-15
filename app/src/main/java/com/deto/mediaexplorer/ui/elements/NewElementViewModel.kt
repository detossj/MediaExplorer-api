package com.deto.mediaexplorer.ui.elements

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.Element
import com.deto.mediaexplorer.data.remote.services.ElementService
import kotlinx.coroutines.launch
import kotlin.Int

sealed class NewElementUiState {
    data class Success(val elements: List<Element>) : NewElementUiState()
    data class Error(val message: String) : NewElementUiState()
    object Loading : NewElementUiState()
    object Idle : NewElementUiState()
}

class NewElementViewModel(private val elementService: ElementService) : ViewModel() {

    var newElementUiState: NewElementUiState by mutableStateOf(NewElementUiState.Loading)
        private set


    fun addElement(title: String, description: String, icon: String, classification: Int, categoryId: Int){

        viewModelScope.launch {
            newElementUiState = NewElementUiState.Loading
            try {
                elementService.addElement(Element(0,title,description,classification,icon, categoryId))
                val updatedList = elementService.getElements()
                newElementUiState = NewElementUiState.Success(updatedList)
            } catch (e: Exception) {
                newElementUiState = NewElementUiState.Error(e.message ?: "error")
            }
        }
    }
    fun resetUiState() {
        newElementUiState = NewElementUiState.Idle
    }
}
