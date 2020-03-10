package com.navigation.eazymarket.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.navigation.eazymarket.domain.Supermarket

@Dao
interface SupermarketDao {
    @Query("SELECT * FROM Supermarket")
    fun all(): List<Supermarket>

    @Insert
    fun add(vararg supermarket: Supermarket)
}