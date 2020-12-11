package com.example.sweetsShop.model.repository

import com.example.sweetsShop.model.entity.User
import com.example.sweetsShop.model.network.ApiFactory

class ClientsRepository {

    suspend fun getAll(): List<User> =
            ApiFactory.apiService.allClients().await()

    suspend fun get(id: Long) =
            ApiFactory.apiService.allClients().await().find { it.id == id }!!
}