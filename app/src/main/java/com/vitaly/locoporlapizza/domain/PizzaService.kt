package com.vitaly.locoporlapizza.domain

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PizzaService {

    var pizzaApi: PizzaApi? = null

    private fun createService(): PizzaApi {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://springboot-kotlin-demo.herokuapp.com/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PizzaApi::class.java)
    }
    fun providePizzaApi(): PizzaApi {
        if (pizzaApi == null) {
            pizzaApi = createService()
        }
        return pizzaApi as PizzaApi
    }
}