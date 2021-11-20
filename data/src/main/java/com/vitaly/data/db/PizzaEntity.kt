package com.vitaly.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vitaly.domain.models.Pizza

@Entity
data class PizzaEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrls: List<String>,
    val description: String,
    val quantity: Int,
){
    fun toPizza(): Pizza {
        return Pizza(id, name, price, imageUrls, description, quantity)
    }
}