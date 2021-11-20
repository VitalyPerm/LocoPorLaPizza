package com.vitaly.data.db

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface PizzaDao {
    @Query("SELECT * FROM PizzaEntity")
    fun getAll(): Observable<List<PizzaEntity>>

    @Query("SELECT * FROM PizzaEntity where id=:id")
    fun getPizzaById(id: Int): Single<PizzaEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(pic: PizzaEntity): Completable

    @Query("DELETE FROM PizzaEntity")
    fun clear(): Completable

    @Update
    fun update(pic: PizzaEntity): Completable
}