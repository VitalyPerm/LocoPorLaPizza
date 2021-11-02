package com.vitaly.locoporlapizza.data.network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PizzaApi {
    @GET("pizza")
    fun getAll(): Single<List<PizzaResponse>>

    @POST("pizza/order")
    fun sendOrder(@Body pizzas: List<PizzaOrderEntity>): Completable
}
