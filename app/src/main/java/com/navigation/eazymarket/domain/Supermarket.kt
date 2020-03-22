package com.navigation.eazymarket.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Supermarket (
    val name: String,
    val description: String
    ): Serializable{

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var isUsing : Boolean = false
}