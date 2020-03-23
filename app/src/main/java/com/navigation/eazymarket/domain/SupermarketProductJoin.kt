package com.navigation.eazymarket.domain

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
primaryKeys = ["supermarket", "product"] ,
foreignKeys = [
    ForeignKey(
        entity = Supermarket::class,
        parentColumns = ["id"],
        childColumns = ["supermarket"]
    ),
    ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],
        childColumns = ["product"]
    )
]
)
data class SupermarketProductJoin(
    val supermarket: Long,
    val product: Long,
    var valueProdut: Double,
    var quantity: Int = 0
) {

}