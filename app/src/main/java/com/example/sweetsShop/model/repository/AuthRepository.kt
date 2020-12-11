package com.example.sweetsShop.model.repository

import com.example.sweetsShop.model.entity.LoginData
import com.example.sweetsShop.model.entity.User
import com.example.sweetsShop.model.item.AuthorizationFailException
import com.example.sweetsShop.model.item.ServerErrorMessage
import com.example.sweetsShop.model.item.UserType
import com.example.sweetsShop.model.network.ApiFactory

class AuthRepository {
    suspend fun loginUser(type: UserType, login: String, password: String): User {
        val responseBody = LoginData(login, password)
        val user = when (type) {
            UserType.CLIENT -> ApiFactory.apiService.loginClient(responseBody).await()
            UserType.CHEF -> ApiFactory.apiService.loginChef(responseBody).await()
            UserType.OPERATOR -> ApiFactory.apiService.loginOperator(responseBody).await()
        }
        user.error?.let { throw AuthorizationFailException() }
        return user
    }

    suspend fun registerUser(type: UserType, user: User): User {
        val user = when (type) {
            UserType.CLIENT -> ApiFactory.apiService.registerClient(user).await()
            UserType.CHEF -> ApiFactory.apiService.registerChef(user).await()
            UserType.OPERATOR -> ApiFactory.apiService.registerOperator(user).await()
        }
        user.error?.let { throw ServerErrorMessage("Пользователь уже существует") }
        return user
    }
}