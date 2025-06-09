package com.deto.mediaexplorer.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.CategoryRepository
import kotlinx.coroutines.launch

class HomeViewModel (private val categoryRepository: CategoryRepository) : ViewModel() {

    val categoriesList = categoryRepository.getAllCategoriesStream()

    fun deleteCategoryById(id: Int) {
        viewModelScope.launch {
            categoryRepository.deleteCategoryById(id)
        }
    }

}