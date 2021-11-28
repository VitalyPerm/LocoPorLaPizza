package com.vitaly.data.db

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface PizzaDao {
    @Query("SELECT * FROM PizzaEntity")
    fun getAll(): Flow<List<PizzaEntity>>

    @Query("SELECT * FROM PizzaEntity where id=:id")
    fun getPizzaById(id: Int): PizzaEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pic: PizzaEntity)

    @Query("DELETE FROM PizzaEntity")
    suspend fun clear()

    @Update
    suspend fun update(pic: PizzaEntity)
}