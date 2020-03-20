package com.navigation.eazymarket.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.navigation.eazymarket.domain.Product
import com.navigation.eazymarket.domain.Supermarket
import com.navigation.eazymarket.domain.SupermarketProduct

@Dao
interface SupermarketProductJoin {
    @Insert
    fun insert(supermarketProduct: SupermarketProduct)

    @Query(  "" +
            "SELECT * FROM PRODUCT " +
            "JOIN SUPERMARKETPRODUCT " +
            "ON PRODUCT.ID = SUPERMARKETPRODUCT.product" +
            "WHRERE SUPERMARKETPRODUCT.supermarket =: supermarketId" )
    fun getProductForSupermarketList(supermarketId: Long): Array<Product>


    @Query( "SELECT * FROM SUPERMARKET" +
            "JOIN SUPERMARKETPRODUCT" +
            "ON SUPERMARKET.ID = SUPERMARKETPRODUCT.supermarket" +
            "WHERE SUPERMARKETPRODUCT.product=:productId")
    fun getSupermarketForProductList(productId: Long): Array<Supermarket>
}