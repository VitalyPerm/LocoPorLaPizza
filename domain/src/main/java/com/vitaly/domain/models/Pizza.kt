package com.vitaly.domain.models


data class Pizza(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrls: List<String>,
    val description: String,
    val quantity: Int
)