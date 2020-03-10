package com.navigation.eazymarket.model

import com.navigation.eazymarket.domain.Supermarket

class SuperMarketDTO(val name: String, val descrition: String) {


    companion object{
        public fun convertEntitytoDto( supermarket: Supermarket): SuperMarketDTO{
            return SuperMarketDTO(supermarket.name, supermarket.description)
        }

        public   fun converListEntityToListDto(supermarkets: List<Supermarket>): List<SuperMarketDTO>{
            val dtoList = ArrayList<SuperMarketDTO>()

            supermarkets.forEach{
                dtoList.add(convertEntitytoDto(it))
            }
            return dtoList
        }

    }

}