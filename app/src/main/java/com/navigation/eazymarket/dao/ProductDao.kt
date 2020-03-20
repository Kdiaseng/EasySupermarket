package com.navigation.eazymarket.dao

import androidx.room.*
import com.navigation.eazymarket.domain.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM PRODUCT")
    fun all(): List<Product>

    @Query("SELECT * FROM PRODUCT " +
            "WHERE PRODUCT.CODE =:codeProduct")
    fun getProductForCode(codeProduct: String):Product?

    @Insert
    fun add(product: Product): Long

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun delete(product: Product)
}

