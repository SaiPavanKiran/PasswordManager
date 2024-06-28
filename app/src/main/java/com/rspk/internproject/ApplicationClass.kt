package com.rspk.internproject

import android.app.Application
import com.rspk.internproject.data.AppContainer
import com.rspk.internproject.data.AppContainerImpl

class ApplicationClass :Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}