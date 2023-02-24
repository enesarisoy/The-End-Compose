package com.ns.theendcompose.data.initializer

import android.app.Application
import com.ns.theendcompose.data.paging.ConfigDataSource
import javax.inject.Inject

class ConfigDataSourceInitializer @Inject constructor(
    private val configDataSource: ConfigDataSource
) : AppInitializer{
    override fun init(application: Application) {
        configDataSource.init()
    }
}