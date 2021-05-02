package com.example.flupertest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flupertest.model.Product

@Database(entities = [Product::class], version = 1)
abstract class MyDatabase : RoomDatabase() {


    abstract fun productDao(): ProductDao

    companion object {
        private const val dbName = "ProductDB"
        private var userDatabase: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (null == userDatabase) {
                userDatabase =
                    buildDatabaseInstance(context)
            }
            return userDatabase
        }


        private fun buildDatabaseInstance(context: Context): MyDatabase? {
            return Room.databaseBuilder(
                context,
                MyDatabase::class.java,
                dbName
            ).allowMainThreadQueries().build()
        }
    }
}