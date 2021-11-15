package com.vitaly.locoporlapizza.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vitaly.locoporlapizza.domain.PizzaEntity

@Database(entities = [PizzaEntity::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class PizzaDataBase : RoomDatabase() {
    abstract fun getPizzaDao(): PizzaDao
}