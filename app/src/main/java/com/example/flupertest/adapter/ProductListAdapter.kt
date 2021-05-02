package com.example.flupertest.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.flupertest.R
import com.example.flupertest.activity.ProductDetail
import com.example.flupertest.database.MyDatabase
import com.example.flupertest.model.Product

class ProductListAdapter(val context: Context, private val productList: List<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var database: MyDatabase

        fun bindItems(product: Product) {

            //********* initialize button **************//
            val tvName = itemView.findViewById(R.id.tv_name) as TextView
            val tvDescription = itemView.findViewById(R.id.tv_description) as TextView
            val tvSalePrice = itemView.findViewById(R.id.tv_sale_price) as TextView
            val tvRegularPrice = itemView.findViewById(R.id.tv_regular_price) as TextView
            val ivProductPhoto = itemView.findViewById(R.id.iv_product_photo) as ImageView


            //********* bind data with UI component **************//
            tvName.text = product.name
            tvDescription.text = product.description
            tvSalePrice.text = product.salePrice
            tvRegularPrice.text = product.regularPrice
            tvName.text = product.name

            //******** set image from url **********//
            Glide
                .with(itemView.context)
                .load(product.productPhoto)
                .centerCrop()
                .placeholder(R.drawable.ic_image)
                .into(ivProductPhoto)

            itemView.setOnClickListener {
                itemView.context.startActivity(
                    Intent(itemView.context, ProductDetail::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra("product", product)
                )
            }

        }
    }
}