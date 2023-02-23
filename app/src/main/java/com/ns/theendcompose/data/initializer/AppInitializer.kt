package com.ns.theendcompose.data.initializer

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}