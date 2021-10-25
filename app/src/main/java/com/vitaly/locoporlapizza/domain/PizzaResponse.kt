package com.vitaly.locoporlapizza.domain

data class PizzaResponse(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrls: List<String>,
    val description: String
)