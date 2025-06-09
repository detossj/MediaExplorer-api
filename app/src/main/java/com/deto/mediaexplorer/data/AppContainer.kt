package com.deto.mediaexplorer.data

import android.content.Context
import com.deto.mediaexplorer.data.remote.ApiClient
import com.deto.mediaexplorer.data.remote.services.AuthService
import com.deto.mediaexplorer.data.remote.services.CategoryService
import com.deto.mediaexplorer.data.remote.services.ElementService

interface AppContainer {
    val categoryRepository : CategoryRepository
    val elementRepository : ElementRepository
    val authApiService: AuthService
    val categoryApiService: CategoryService
    val elementApiService: ElementService
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val categoryRepository: CategoryRepository by lazy {
        LocalCategoryRepository(AppDatabase.getDatabase(context).categoryDao())
    }

    override val elementRepository: ElementRepository by lazy {
        LocalElementRepository(AppDatabase.getDatabase(context).elementDao())
    }

    override val authApiService: AuthService by lazy {
        ApiClient.create(context).create(AuthService::class.java)
    }

    override val categoryApiService: CategoryService by lazy {
        ApiClient.create(context).create(CategoryService::class.java)
    }

    override val elementApiService: ElementService by lazy {
        ApiClient.create(context).create(ElementService::class.java)
    }
}

