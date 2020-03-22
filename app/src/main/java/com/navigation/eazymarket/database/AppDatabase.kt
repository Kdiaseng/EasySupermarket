package com.navigation.eazymarket.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.navigation.eazymarket.dao.ProductDao
import com.navigation.eazymarket.dao.SupermarketDao
import com.navigation.eazymarket.dao.SupermarketProductJoinDao
import com.navigation.eazymarket.domain.Product
import com.navigation.eazymarket.domain.Supermarket
import com.navigation.eazymarket.domain.SupermarketProductJoin

@Database(entities = [Supermarket::class, Product :: class, SupermarketProductJoin ::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun supermarketDao(): SupermarketDao
    abstract fun productDao(): ProductDao
    abstract fun supermarketProductJoinDao(): SupermarketProductJoinDao

    companion object{
       @Volatile private var instance : AppDatabase? = null

        private  val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: builDatabase(context).also{
                instance = it
            }
        }

        private fun builDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase:: class.java,
            "easy-supermarket-database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration()
            .build()
    }
}