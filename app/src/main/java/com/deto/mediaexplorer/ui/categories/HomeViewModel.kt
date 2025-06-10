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

sealed class CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState()
    data class Error(val message: String) : CategoriesUiState()
    object Loading : CategoriesUiState()
    object Idle : CategoriesUiState()
}


/*
class HomeViewModel (private val categoryRepository: CategoryRepository) : ViewModel() {

    val categoriesList = categoryRepository.getAllCategoriesStream()

    fun deleteCategoryById(id: Int) {
        viewModelScope.launch {
            categoryRepository.deleteCategoryById(id)
        }
    }



}*/

class HomeViewModel(
    private val categoryService: CategoryService
) : ViewModel(){
    var categoriesUiState: CategoriesUiState by mutableStateOf(CategoriesUiState.Loading)
        private set

    fun resetUiState() {
        categoriesUiState = CategoriesUiState.Idle
    }

    private fun getCategories(){
        viewModelScope.launch {
            categoriesUiState = CategoriesUiState.Loading
            categoriesUiState = try {
                val listCategories = categoryService.getCategories()
                CategoriesUiState.Success(listCategories)
            } catch (e: Exception) {
                CategoriesUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun refreshCategories() {
        getCategories()
    }

    fun addCategory(name: String) {
        viewModelScope.launch {
            categoriesUiState = CategoriesUiState.Loading
            try {
                categoryService.addCategory(Category(0,name,null))
                val updatedList = categoryService.getCategories()
                categoriesUiState = CategoriesUiState.Success(updatedList)
            } catch (e: Exception) {
                categoriesUiState = CategoriesUiState.Error(e.message ?: "error")
            }
        }
    }
}
