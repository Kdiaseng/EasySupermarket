package com.navigation.eazymarket.dao

import androidx.room.*
import com.navigation.eazymarket.domain.Product
import com.navigation.eazymarket.domain.Supermarket
import com.navigation.eazymarket.domain.SupermarketProductJoin
import com.navigation.eazymarket.model.ProductDTO

@Dao
interface SupermarketProductJoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(supermarketProductJoin: SupermarketProductJoin)

    @Update
    fun update(supermarketProductJoin: SupermarketProductJoin)

    @Query(  "" +
            "SELECT * FROM PRODUCT " +
            "JOIN SUPERMARKETPRODUCTJOIN " +
            "ON PRODUCT.ID = SUPERMARKETPRODUCTJOIN.product " +
            "WHERE SUPERMARKETPRODUCTJOIN.supermarket =:supermarketId " )
    fun getProductForSupermarketList(supermarketId: Long): Array<Product>

    @Query( "SELECT * FROM SUPERMARKET " +
            "JOIN SUPERMARKETPRODUCTJOIN " +
            "ON SUPERMARKET.ID = SUPERMARKETPRODUCTJOIN.supermarket " +
            "WHERE SUPERMARKETPRODUCTJOIN.product=:productId")
    fun getSupermarketForProductList(productId: Long): Array<Supermarket>

    @Query(  "" +
            "SELECT * FROM PRODUCT " +
            "JOIN SUPERMARKETPRODUCTJOIN " +
            "ON PRODUCT.ID = SUPERMARKETPRODUCTJOIN.product " +
            "WHERE SUPERMARKETPRODUCTJOIN.supermarket=:supermarketId AND PRODUCT.CODE=:code " )
    fun getProductForIdSupermarketFromCode(supermarketId: Long, code: String): Array<Product>


    @Query(  "" +
            "SELECT * FROM SUPERMARKETPRODUCTJOIN " +
            "WHERE SUPERMARKETPRODUCTJOIN.supermarket=:supermarketId AND SUPERMARKETPRODUCTJOIN.product=:productId " )
    fun verifyProductInSupermarket(supermarketId: Long, productId: Long): SupermarketProductJoin

    @Query("SELECT PRODUCT.name, SUPERMARKETPRODUCTJOIN.supermarket, SUPERMARKETPRODUCTJOIN.product, " +
            "PRODUCT.description, SUPERMARKETPRODUCTJOIN.valueProdut, " +
            "SUPERMARKETPRODUCTJOIN.quantity from PRODUCT JOIN SUPERMARKETPRODUCTJOIN ON " +
            "PRODUCT.ID = SUPERMARKETPRODUCTJOIN.product " +
            "WHERE SUPERMARKETPRODUCTJOIN.quantity > 0 ")
    fun getAllProductDTO(): List<ProductDTO>

    @Query("UPDATE SUPERMARKETPRODUCTJOIN SET quantity = 0 WHERE supermarket=:idSupermarket ")
    fun finishListCat(idSupermarket: Long)

    @Query("DELETE FROM SUPERMARKETPRODUCTJOIN WHERE supermarket=:id")
    fun deleteAllRegisterByIdSupermarlet(id: Long)


}