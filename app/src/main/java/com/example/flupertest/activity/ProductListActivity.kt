package com.example.flupertest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flupertest.R
import com.example.flupertest.adapter.ProductListAdapter
import com.example.flupertest.database.MyDatabase
import com.example.flupertest.model.Product

class ProductListActivity : AppCompatActivity() {
    private lateinit var rcvProduct: RecyclerView
    private lateinit var productList: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        getViewById()
        fetchProductFromTable()
        setProductWithRecyclerview()
    }

    private fun setProductWithRecyclerview() {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcvProduct.layoutManager = layoutManager
        val adapter = ProductListAdapter(this, productList)
        rcvProduct.adapter = adapter
    }

    private fun fetchProductFromTable() {
        val database: MyDatabase = MyDatabase.getInstance(this)!!

        //fetch Records
        productList = database.productDao().getAllProducts()
    }

    private fun getViewById() {
        rcvProduct = findViewById(R.id.rcv_product)
    }
}