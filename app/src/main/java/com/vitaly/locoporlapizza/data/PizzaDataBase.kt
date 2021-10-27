package com.vitaly.locoporlapizza.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PizzaEntity::class], version = 1)
abstract class PizzaDataBase : RoomDatabase() {
    abstract fun getPizzaDao(): PizzaDao

    companion object {
        private var db_instance: PizzaDataBase? = null

        @Synchronized
        fun getDatabaseInstance(context: Context): PizzaDataBase {
            if (db_instance == null) {
                db_instance = Room.databaseBuilder(
                    context.applicationContext, PizzaDataBase::class.java, "db"
                ).build()
            }
            return db_instance as PizzaDataBase
        }
    }
}