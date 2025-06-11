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

sealed class NewCategoryUiState {
    data class Success(val categories: List<Category>) : NewCategoryUiState()
    data class Error(val message: String) : NewCategoryUiState()
    object Loading : NewCategoryUiState()
    object Idle : NewCategoryUiState()
}
class NewCategoryViewModel (private val categoryService: CategoryService) : ViewModel() {

    var newCategoryUiState: NewCategoryUiState by mutableStateOf(NewCategoryUiState.Loading)
        private set

    fun addCategory(name: String) {
        viewModelScope.launch {
            newCategoryUiState = NewCategoryUiState.Loading
            try {
                categoryService.addCategory(Category(0,name,null))
                val updatedList = categoryService.getCategories()
                newCategoryUiState = NewCategoryUiState.Success(updatedList)
            } catch (e: Exception) {
                newCategoryUiState = NewCategoryUiState.Error(e.message ?: "error")
            }
        }
    }
    fun resetUiState() {
        newCategoryUiState = NewCategoryUiState.Idle
    }

}