package com.deto.mediaexplorer

import android.app.Application
import com.deto.mediaexplorer.data.AppContainer
import com.deto.mediaexplorer.data.AppDataContainer

class MediaExplorerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}