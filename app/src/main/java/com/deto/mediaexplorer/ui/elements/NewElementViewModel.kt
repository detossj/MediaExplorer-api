package com.deto.mediaexplorer.ui.elements

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.deto.mediaexplorer.data.Element
import com.deto.mediaexplorer.data.ElementRepository
import kotlin.Int

class NewElementViewModel(private val elementRepository: ElementRepository) : ViewModel() {

    var newElementUiState by mutableStateOf(NewElementUIState())
        private set

    fun updateUiState(newElement: NewElement){
        newElementUiState =
            NewElementUIState(newElement = newElement, isEntryValid = validateInput(newElement))
    }

    suspend fun saveItem() {
        if(validateInput()){
            elementRepository.insertElement(newElementUiState.newElement.toElement())
        }
    }

    private fun validateInput(uiState: NewElement = newElementUiState.newElement) : Boolean {
        return with(uiState) {
            title.isNotBlank() && description.isNotBlank()
        }
    }
    
}
data class NewElementUIState(
    val newElement: NewElement = NewElement(),
    val isEntryValid: Boolean = false
)

data class NewElement(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val classification: Int = 1,
    val imagen: Int? = null,
    val categoryId: Int = 0
)

fun NewElement.toElement(): Element = Element(
    id = id,
    title = title,
    description = description,
    classification = classification,
    imagen = imagen,
    categoryId = categoryId
)

fun Element.toItemUiState(isEntryValid: Boolean = false): NewElementUIState = NewElementUIState(
    newElement = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Element.toItemDetails(): NewElement = NewElement(
    id = id,
    title = title,
    description = description,
    classification = classification,
    imagen = imagen,
    categoryId = categoryId
)