package com.vitaly.locoporlapizza.data

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface PizzaDao {
    @Query("SELECT * FROM PizzaEntity")
    fun getAll(): Observable<List<PizzaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pic: PizzaEntity)


    @Delete
    fun delete(pic: PizzaEntity)
}