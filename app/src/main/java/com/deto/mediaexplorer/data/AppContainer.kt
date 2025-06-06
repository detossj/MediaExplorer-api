package com.deto.mediaexplorer.data

import android.content.Context

interface AppContainer {
    val categoryRepository : CategoryRepository
    val elementRepository : ElementRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val categoryRepository: CategoryRepository by lazy {
        LocalCategoryRepository(AppDatabase.getDatabase(context).categoryDao())
    }

    override val elementRepository: ElementRepository by lazy {
        LocalElementRepository(AppDatabase.getDatabase(context).elementDao())
    }
}

