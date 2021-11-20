package com.vitaly.data

import com.vitaly.data.db.PizzaEntity
import com.vitaly.data.network.PizzaOrderEntity
import com.vitaly.data.network.PizzaResponse
import com.vitaly.domain.models.Pizza
import com.vitaly.domain.models.PizzaOrder

fun mapResponseToPizza(pizzaList: List<PizzaResponse>): List<Pizza> {
    val list = mutableListOf<Pizza>()
    pizzaList.forEach { pizza -> list.add(Pizza(pizza.id, pizza.name, pizza.price, pizza.imageUrls, pizza.description, 0)) }
    return list
}

fun mapEntityToPizza(pizzaList: List<PizzaEntity>): List<Pizza> {
    val list = mutableListOf<Pizza>()
    pizzaList.forEach { pizza -> list.add(Pizza(pizza.id, pizza.name, pizza.price, pizza.imageUrls, pizza.description, pizza.quantity)) }
    return list
}

fun mapOrder(pizzaList: List<PizzaOrder>): List<PizzaOrderEntity>{
    val list = mutableListOf<PizzaOrderEntity>()
    pizzaList.forEach { pizza -> list.add(PizzaOrderEntity(pizza.pizzaId, pizza.quantity)) }
    return list
}

fun mapSinglePizza(pizza: Pizza): PizzaEntity {
    return PizzaEntity(pizza.id, pizza.name, pizza.price, pizza.imageUrls, pizza.description, pizza.quantity)
}