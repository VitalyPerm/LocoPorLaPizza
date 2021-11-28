package com.vitaly.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PizzaApi {
    @GET("pizza")
    suspend fun getAll(): List<PizzaResponse>

    @POST("pizza/order")
    suspend fun sendOrder(@Body pizzas: List<PizzaOrderEntity>)
}
