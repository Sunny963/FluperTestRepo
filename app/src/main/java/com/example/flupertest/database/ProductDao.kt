package com.example.flupertest.database

import androidx.room.*
import com.example.flupertest.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Query(value = "Select * from Product")
    fun getAllProducts(): List<Product>

    @Delete
    fun delete(product: Product)

    @Query("UPDATE Product SET name = :name, description = :description, salePrice = :salePrice, regularPrice = :regularPrice WHERE Id =:Id")
    fun update(
        name: String?,
        description: String?,
        salePrice: String?,
        regularPrice: String?,
        Id: String?
    )
}