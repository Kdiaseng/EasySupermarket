package com.navigation.eazymarket.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    val code: String,
    val name: String,
    val description: String,
    val valueUnit: Double
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
