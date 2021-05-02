package com.example.flupertest.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flupertest.R
import com.example.flupertest.adapter.ProductCreationAdapter
import com.example.flupertest.database.MyDatabase
import com.example.flupertest.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnShowProduct: Button
    private lateinit var rcvCreateProduct: RecyclerView

    private lateinit var productList: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeDatabase()
        getViewById()
        setClickListenerOnView()
        getJsonDataWithProductList()
        bindProductWithRecyclerView(productList)
    }

    private fun initializeDatabase() {
        MyDatabase.getInstance(applicationContext)
    }


    private fun getJsonDataWithProductList() {
        //******** get json data from assets ***********//
        val jsonString: String? = loadJSONFromAsset()
        Log.d("sha", "json data: $jsonString")

        //********** get product list *****************//
        val listType: Type = object : TypeToken<List<Product?>?>() {}.type
        productList = Gson().fromJson(jsonString, listType)
        Log.d("sha", "product list: $productList")
    }

    private fun bindProductWithRecyclerView(productList: ArrayList<Product>) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcvCreateProduct.layoutManager = layoutManager
        val adapter = ProductCreationAdapter(this, productList)
        rcvCreateProduct.adapter = adapter
    }

    private fun setClickListenerOnView() {
        btnShowProduct.setOnClickListener(this)
    }

    private fun getViewById() {
        btnShowProduct = findViewById(R.id.btn_show_product)
        rcvCreateProduct = findViewById(R.id.rcv_create_product)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_show_product -> {
                startActivity(
                    Intent(this, ProductListActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
            }
        }
    }

    private fun loadJSONFromAsset(): String? {
        return try {
            val `is`: InputStream = assets.open("product.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}