package com.navigation.eazymarket.model

import androidx.room.ColumnInfo
import java.io.Serializable

data class ProductDTO(
    @ColumnInfo(name = "name")
    var nameProduct: String,

    @ColumnInfo(name = "product")
    var productId: Long,

    @ColumnInfo(name = "supermarket")
    var supermarketId: Long,

    @ColumnInfo(name = "description")
    var descriptionProduct: String,

    @ColumnInfo(name = "valueProdut")
    var valueUnitProduct: Double,

    @ColumnInfo(name = "quantity")
    var quantityProduct: Int): Serializable {
}