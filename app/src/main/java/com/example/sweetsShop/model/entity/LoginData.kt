package com.example.sweetsShop.model.entity

import com.google.gson.annotations.Expose

data class LoginData(
        @Expose val login: String,
        @Expose val password: String
)