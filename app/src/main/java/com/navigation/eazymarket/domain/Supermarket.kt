package com.navigation.eazymarket.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Supermarket (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String
    ){

}