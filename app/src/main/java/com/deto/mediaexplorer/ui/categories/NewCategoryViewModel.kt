package com.deto.mediaexplorer.ui.categories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.Category
import com.deto.mediaexplorer.data.CategoryRepository
import com.deto.mediaexplorer.data.remote.services.CategoryService
import kotlinx.coroutines.launch

sealed class CategoryUiState {
    data class Success(val categories: List<Category>) : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
    object Loading : CategoryUiState()
    object Idle : CategoryUiState()
}
class NewCategoryViewModel (private val categoryService: CategoryService) : ViewModel() {

    var categoryUiState: CategoryUiState by mutableStateOf(CategoryUiState.Loading)
        private set

    fun addCategory(name: String) {
        viewModelScope.launch {
            categoryUiState = CategoryUiState.Loading
            try {
                categoryService.addCategory(Category(0,name,null))
                val updatedList = categoryService.getCategories()
                categoryUiState = CategoryUiState.Success(updatedList)
            } catch (e: Exception) {
                categoryUiState = CategoryUiState.Error(e.message ?: "error")
            }
        }
    }
    fun resetUiState() {
        categoryUiState = CategoryUiState.Idle
    }

}