package com.example.sweetsShop.model.item

import kotlinx.coroutines.CoroutineExceptionHandler
import com.example.sweetsShop.model.Logger

val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Logger.e(throwable) }

fun exceptionHandler(block: () -> Unit) = CoroutineExceptionHandler { _, throwable ->
    Logger.e(throwable)
    block()
}