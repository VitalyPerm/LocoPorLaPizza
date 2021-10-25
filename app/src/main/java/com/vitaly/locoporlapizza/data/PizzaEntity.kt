package com.vitaly.locoporlapizza.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PizzaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val price: Double,
    @ColumnInfo
    val imageUrl: String,
    @ColumnInfo
    val description: String
)