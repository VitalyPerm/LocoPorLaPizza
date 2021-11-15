package com.vitaly.presentation.utils

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.vitaly.domain.models.PizzaOrderEntity
import com.vitaly.domain.models.PizzaResponse
import com.vitaly.domain.models.PizzaEntity

fun ImageView.loadPicture(url: String?, progressDrawable: CircularProgressDrawable) {
    Glide.with(this).load(url).placeholder(progressDrawable).into(this)
}

fun pizzaMapper(pizza: PizzaResponse): PizzaEntity {
    return PizzaEntity(
        name = pizza.name, description = pizza.description, price = pizza.price,
        imageUrls = pizza.imageUrls, quantity = 0, id = pizza.id
    )
}

fun addPizzaMapper(pizza: PizzaEntity): PizzaEntity {
    return PizzaEntity(
        id = pizza.id, name = pizza.name, description = pizza.description,
        imageUrls = pizza.imageUrls, price = pizza.price, quantity = pizza.quantity + 1
    )
}

fun editPizzaQuantity(pizzaEntity: PizzaEntity, add: Boolean): PizzaEntity {
    //if true increase false decrease
    return if (add) {
        PizzaEntity(
            pizzaEntity.id,
            pizzaEntity.name, pizzaEntity.price, pizzaEntity.imageUrls,
            pizzaEntity.description, pizzaEntity.quantity + 1
        )
    } else {
        PizzaEntity(
            pizzaEntity.id,
            pizzaEntity.name, pizzaEntity.price, pizzaEntity.imageUrls,
            pizzaEntity.description, pizzaEntity.quantity - 1
        )
    }
}

fun prepareOrderEntity(pizzaEntity: PizzaEntity): PizzaOrderEntity {
    return (PizzaOrderEntity(pizzaId = pizzaEntity.id, quantity = pizzaEntity.quantity))
}