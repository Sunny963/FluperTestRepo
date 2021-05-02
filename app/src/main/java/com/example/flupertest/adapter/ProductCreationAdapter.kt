package com.example.flupertest.adapter

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.flupertest.R
import com.example.flupertest.database.MyDatabase
import com.example.flupertest.model.Product

class ProductCreationAdapter(val context: Context, private val productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductCreationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.create_product_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(productList[position], position)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var database: MyDatabase

        fun bindItems(product: Product, position: Int) {

            //********* initialize button **************//
            val btnCreateProduct = itemView.findViewById(R.id.btn_create_product) as Button

            //*********** set product serial number on button to create accordingly *********//
            btnCreateProduct.text =
                itemView.context.getString(R.string.create_product).plus(" ").plus(position + 1)

            //************* insert product into database *****************//
            btnCreateProduct.setOnClickListener {
                val thread = Thread {
                    val productFromJson = Product(
                        product.Id,
                        product.name,
                        product.description,
                        product.salePrice,
                        product.regularPrice,
                        product.productPhoto
                    )
                    database = MyDatabase.getInstance(itemView.context)!!

                    //fetch Records
                    val productList: List<Product> = database.productDao().getAllProducts()
                    if (!productList.contains(product))
                        database.productDao().insertProduct(productFromJson)
                    else {
                        Looper.prepare()
                        Toast.makeText(itemView.context, "Already exist", Toast.LENGTH_SHORT).show()
                    }


                }
                thread.start()
            }
        }
    }
}