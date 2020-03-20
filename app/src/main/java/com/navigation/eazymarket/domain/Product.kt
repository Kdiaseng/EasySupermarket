package com.navigation.eazymarket.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Product(
    val code: String,
    val name: String,
    val description: String,
    val valueUnit: Double
): Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
