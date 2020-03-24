package com.navigation.eazymarket.model

import java.io.Serializable

data class ProductDTO(
    var nameProduct: String,
    var descriptionProduct: String,
    var valueUnitProduct: Double,
    var quantityProduct: Int): Serializable {
}