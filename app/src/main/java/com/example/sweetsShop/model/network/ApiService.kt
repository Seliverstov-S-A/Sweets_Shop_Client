package com.example.sweetsShop.model.network

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.sweetsShop.model.entity.Order
import com.example.sweetsShop.model.entity.LoginData
import com.example.sweetsShop.model.entity.User


interface ApiService {

    @POST("clients/sign")
    fun loginClient(@Body data: LoginData): Deferred<User>

    @POST("chefs/sign")
    fun loginChef(@Body data: LoginData): Deferred<User>

    @POST("operators/sign")
    fun loginOperator(@Body data: LoginData): Deferred<User>

    @POST("clients/create")
    fun registerClient(@Body user: User): Deferred<User>

    @POST("chefs/create")
    fun registerChef(@Body user: User): Deferred<User>

    @POST("operators/create")
    fun registerOperator(@Body user: User): Deferred<User>

    @GET("orders/all")
    fun allOrders(): Deferred<List<Order>>

    @GET("orders/{id}")
    fun getOrder(@Path(value = "id") walkId: Long): Deferred<Order>

    @POST("orders/create")
    fun createOrder(@Body order: Order): Deferred<Order>

    @POST("orders/change")
    fun updateOrder(@Body order: Order): Deferred<Order>

    @GET("chefs/all")
    fun allChefs(): Deferred<List<User>>

    @GET( "clients/all")
    fun allClients(): Deferred<List<User>>
}