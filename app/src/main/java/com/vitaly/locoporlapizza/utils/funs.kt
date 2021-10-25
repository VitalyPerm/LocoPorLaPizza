package com.vitaly.locoporlapizza.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vitaly.locoporlapizza.data.PizzaEntity
import com.vitaly.locoporlapizza.domain.PizzaResponse

fun ImageView.loadPicture(url: String?) {
    if (url != null) {
        Glide.with(this).load(url).into(this)
    }
}

fun pizzaMapper(pizza: PizzaResponse): PizzaEntity {
    return PizzaEntity(name = pizza.name, description = pizza.description, price = pizza.price, imageUrl = pizza.imageUrls[0])
}