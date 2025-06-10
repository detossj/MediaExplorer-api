package com.deto.mediaexplorer.ui.elements

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.Element
import com.deto.mediaexplorer.data.remote.services.ElementService
import kotlinx.coroutines.launch

sealed class ElementUiState {
    data class Success(val element: Element) : ElementUiState()
    data class Error(val message: String) : ElementUiState()
    object Loading : ElementUiState()
    object Idle : ElementUiState()
}

class ElementViewModel(
    private val elementService: ElementService
) : ViewModel() {

    var elementUiState: ElementUiState by mutableStateOf(ElementUiState.Loading)
        private set

    internal fun getElementsById(id: Int){
        viewModelScope.launch {
            elementUiState = ElementUiState.Loading
            elementUiState = try {
                val filteredElement = elementService.getElementById(id)
                ElementUiState.Success(filteredElement)
            } catch (e: Exception) {
                ElementUiState.Error(e.message ?: "error")
            }
        }
    }
}