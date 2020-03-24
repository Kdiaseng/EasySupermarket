package com.navigation.eazymarket.model

import java.io.Serializable

data class RegisterProductParam(val supermarketId: Long, val code: String): Serializable {
}