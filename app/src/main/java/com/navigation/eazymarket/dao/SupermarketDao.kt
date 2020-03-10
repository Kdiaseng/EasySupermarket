package com.navigation.eazymarket.dao

import androidx.room.*
import com.navigation.eazymarket.domain.Supermarket

@Dao
interface SupermarketDao {
    @Query("SELECT * FROM Supermarket")
    fun all(): List<Supermarket>

    @Insert
    fun add(vararg supermarket: Supermarket)

    @Update
    fun updateSupermarket(supermarket: Supermarket)

    @Delete
    fun delete(supermarket: Supermarket)
}