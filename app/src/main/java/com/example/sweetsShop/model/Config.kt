package com.example.sweetsShop.model

object Config {

    val apiUrl: String
        get() = "http://${Preferences.serverHost}:${Preferences.serverPort}/api/"
}