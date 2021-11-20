package com.vitaly.presentation.utils

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.vitaly.domain.models.Pizza

fun ImageView.loadPicture(url: String?, progressDrawable: CircularProgressDrawable) {
    Glide.with(this).load(url).placeholder(progressDrawable).into(this)
}

fun editPizzaQuantity(pizza: Pizza, add: Boolean): Pizza {
    //if true increase false decrease
    return if (add) {
        Pizza(
            pizza.id,
            pizza.name, pizza.price, pizza.imageUrls,
            pizza.description, pizza.quantity + 1
        )
    } else {
        Pizza(
            pizza.id,
            pizza.name, pizza.price, pizza.imageUrls,
            pizza.description, pizza.quantity - 1
        )
    }
}