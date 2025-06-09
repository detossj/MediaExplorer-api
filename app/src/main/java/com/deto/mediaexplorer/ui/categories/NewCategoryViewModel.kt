package com.deto.mediaexplorer.ui.categories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.deto.mediaexplorer.data.Category
import com.deto.mediaexplorer.data.CategoryRepository

class NewCategoryViewModel (private val categoryRepository: CategoryRepository) : ViewModel() {

    var newCategoryUiState by mutableStateOf(NewCategoryUIState())
        private set

    fun updateUiState(newCategory: NewCategory) {
        newCategoryUiState =
            NewCategoryUIState(newCategory = newCategory, isEntryValid = validateInput(newCategory) )
    }

    suspend fun saveItem() {
        if(validateInput()){
            categoryRepository.insertCategory(newCategoryUiState.newCategory.toCategory())
        }
    }

    private fun validateInput(uiState: NewCategory = newCategoryUiState.newCategory) : Boolean {
        return with(uiState) {
            title.isNotBlank()
        }
    }
}

data class NewCategoryUIState(
    val newCategory: NewCategory = NewCategory(),
    val isEntryValid: Boolean = false
)

data class NewCategory(
    val id: Int = 0,
    val title: String = "",
    val icon: Int? = null
)

fun NewCategory.toCategory(): Category = Category(
    id = id,
    title = title,
    icon = icon
)

fun Category.toItemUiState(isEntryValid: Boolean = false): NewCategoryUIState = NewCategoryUIState(
    newCategory = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Category.toItemDetails(): NewCategory = NewCategory(
    id = id,
    title = title,
    icon = icon
)