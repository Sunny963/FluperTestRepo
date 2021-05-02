package com.example.flupertest.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.flupertest.R
import com.example.flupertest.database.MyDatabase
import com.example.flupertest.model.Product

class ProductDetail : AppCompatActivity(), View.OnClickListener {
    private lateinit var ivImage: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvSalePrice: TextView
    private lateinit var tvRegularPrice: TextView
    private lateinit var ivDelete: ImageView
    private lateinit var ivEdit: ImageView
    private lateinit var product: Product
    private lateinit var database: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        initializeDatabase()
        getViewById()
        setClickListenerOnView()
        getIntentData()
        bindDataWithUI()
    }

    private fun initializeDatabase() {
        database = MyDatabase.getInstance(this)!!
    }

    private fun setClickListenerOnView() {
        ivImage.setOnClickListener(this)
        ivDelete.setOnClickListener(this)
        ivEdit.setOnClickListener(this)
    }

    private fun bindDataWithUI() {
        //******** set image from url **********//
        Glide
            .with(this)
            .load(product.productPhoto)
            .centerCrop()
            .placeholder(R.drawable.ic_image)
            .into(ivImage)

        tvName.text = getString(R.string.name).plus(": ").plus(product.name)
        tvDescription.text = getString(R.string.description).plus(": ").plus(product.description)
        tvSalePrice.text = getString(R.string.sale_price).plus(": ").plus(product.salePrice)
        tvRegularPrice.text =
            getString(R.string.regular_price).plus(": ").plus(product.regularPrice)
    }

    private fun getIntentData() {
        product = intent.getParcelableExtra("product")
    }

    private fun getViewById() {
        ivImage = findViewById(R.id.iv_image)
        tvName = findViewById(R.id.tv_name)
        tvDescription = findViewById(R.id.tv_description)
        tvSalePrice = findViewById(R.id.tv_sale_price)
        tvRegularPrice = findViewById(R.id.tv_regular_price)
        ivDelete = findViewById(R.id.iv_delete)
        ivEdit = findViewById(R.id.iv_edit)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_image -> openFullImage()

            R.id.iv_delete -> deleteProduct()

            R.id.iv_edit -> updateProduct()
        }
    }

    private fun updateProduct() {
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.product_data)
        if (dialog.window != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            //********** initialise UI ************//
            val etName: EditText = dialog.findViewById(R.id.et_name)
            val etDescription: EditText = dialog.findViewById(R.id.et_description)
            val etSalePrice: EditText = dialog.findViewById(R.id.et_sale_price)
            val etRegularPrice: EditText = dialog.findViewById(R.id.et_regular_price)
            val btnUpdate: Button = dialog.findViewById(R.id.btn_update)

            etName.setText(product.name)
            etDescription.setText(product.description)
            etSalePrice.setText(product.salePrice)
            etRegularPrice.setText(product.regularPrice)

            btnUpdate.setOnClickListener {
                when {
                    etName.text.isEmpty() -> {
                        etName.error = getString(R.string.error)
                    }
                    etDescription.text.isEmpty() -> {
                        etDescription.error = getString(R.string.error)
                    }
                    etSalePrice.text.isEmpty() -> {
                        etSalePrice.error = getString(R.string.error)
                    }
                    etRegularPrice.text.isEmpty() -> {
                        etRegularPrice.error = getString(R.string.error)
                    }
                    else -> {
                        Toast.makeText(this, product.name + " updated", Toast.LENGTH_SHORT).show()
                        database.productDao()
                            .update(
                                etName.text.toString().trim(),
                                etDescription.text.toString().trim(),
                                etSalePrice.text.toString().trim(),
                                etRegularPrice.text.toString().toString(),
                                product.Id
                            )
                        dialog.cancel()
                        startActivity(
                            Intent(this, ProductListActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                        finish()
                    }
                }
            }

        }
        dialog.show()
    }

    private fun deleteProduct() {
        Toast.makeText(this, product.name + " deleted", Toast.LENGTH_SHORT).show()
        database.productDao().delete(product)
        startActivity(
            Intent(this, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    private fun openFullImage() {
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.image_viewer)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            val productImage: ImageView = dialog.findViewById(R.id.iv_image)

            Glide
                .with(this)
                .load(product.productPhoto)
                .centerCrop()
                .placeholder(R.drawable.ic_image)
                .into(productImage)

            productImage.setOnClickListener {
                dialog.cancel()
            }
        }
        dialog.show()
    }
}