package com.example.sweetsShop.model.repository

import com.example.sweetsShop.model.entity.User
import com.example.sweetsShop.model.network.ApiFactory

class ChefsRepository {

    suspend fun getAll(): List<User> =
            ApiFactory.apiService.allChefs().await()

    suspend fun get(id: Long) =
            ApiFactory.apiService.allChefs().await().find { it.id == id }!!
}