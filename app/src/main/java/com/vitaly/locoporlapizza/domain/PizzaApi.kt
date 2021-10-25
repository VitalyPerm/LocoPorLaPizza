package com.vitaly.locoporlapizza.domain

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PizzaApi {
    @GET("pizza")
    fun getAll(): Single<List<PizzaResponse>>

    @GET("pizza/{id}")
    fun getPizzaById(
        @Path("id") id: Int
    ): Single<PizzaResponse>
}
