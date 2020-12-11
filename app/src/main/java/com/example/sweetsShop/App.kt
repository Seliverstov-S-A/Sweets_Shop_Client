package com.example.sweetsShop

import android.app.Application
import com.example.sweetsShop.model.Preferences

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Preferences.initialize(this)
    }
}