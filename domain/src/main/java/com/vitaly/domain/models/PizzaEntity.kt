package com.vitaly.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PizzaEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrls: List<String>,
    val description: String,
    val quantity: Int,
)