package com.deto.mediaexplorer.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.deto.mediaexplorer.MediaExplorerApplication
import com.deto.mediaexplorer.ui.screens.ElementViewModel
import com.deto.mediaexplorer.ui.screens.HomeViewModel
import com.deto.mediaexplorer.ui.screens.NewCategoryViewModel
import com.deto.mediaexplorer.ui.screens.NewElementViewModel
import com.deto.mediaexplorer.ui.screens.SecondViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer{
            HomeViewModel(App().container.categoryRepository)
        }
        initializer {
            NewCategoryViewModel(App().container.categoryRepository)
        }
        initializer {
            SecondViewModel(App().container.elementRepository)
        }
        initializer {
            NewElementViewModel(App().container.elementRepository)
        }
        initializer {
            ElementViewModel(App().container.elementRepository)
        }
    }

}

fun CreationExtras.App(): MediaExplorerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MediaExplorerApplication)