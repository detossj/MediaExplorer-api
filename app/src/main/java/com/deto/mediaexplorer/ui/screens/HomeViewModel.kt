package com.deto.mediaexplorer.ui.screens
import com.deto.mediaexplorer.R

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.Category
import com.deto.mediaexplorer.data.CategoryRepository
import com.deto.mediaexplorer.data.CategoryWithElements
import com.deto.mediaexplorer.data.Element
import com.deto.mediaexplorer.data.LocalCategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (private val categoryRepository: CategoryRepository) : ViewModel() {

    val categoriesList = categoryRepository.getAllCategoriesStream()

    fun deleteCategoryById(id: Int) {
        viewModelScope.launch {
            categoryRepository.deleteCategoryById(id)
        }
    }

}