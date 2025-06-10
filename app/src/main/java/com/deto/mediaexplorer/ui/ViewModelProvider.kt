package com.deto.mediaexplorer.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.deto.mediaexplorer.MediaExplorerApplication
import com.deto.mediaexplorer.data.remote.services.CategoryService
import com.deto.mediaexplorer.ui.auth.AuthViewModel
import com.deto.mediaexplorer.ui.elements.ElementViewModel
import com.deto.mediaexplorer.ui.categories.HomeViewModel
import com.deto.mediaexplorer.ui.categories.NewCategoryViewModel
import com.deto.mediaexplorer.ui.elements.NewElementViewModel
import com.deto.mediaexplorer.ui.elements.SecondViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer{
            HomeViewModel(App().container.categoryApiService)
        }
        initializer {
            NewCategoryViewModel(App().container.categoryRepository)
        }
        initializer {
            SecondViewModel(App().container.elementApiService)
        }
        initializer {
            NewElementViewModel(App().container.elementRepository)
        }
        initializer {
            ElementViewModel(App().container.elementApiService)
        }
        initializer {
            AuthViewModel(App().container.authApiService, App().baseContext)
        }
    }

}

fun CreationExtras.App(): MediaExplorerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MediaExplorerApplication)