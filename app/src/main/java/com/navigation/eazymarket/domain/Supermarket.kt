package com.navigation.eazymarket.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Supermarket (
    val name: String,
    val description: String
    ){

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}