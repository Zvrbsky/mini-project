package com.example.mini_project.addProduct

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_project.BuildConfig
import com.example.mini_project.R
import com.google.android.material.textfield.TextInputEditText


const val PRODUCT_NAME = "name"
const val PRODUCT_PRICE = "price"
const val PRODUCT_AMOUNT = "amount"

class AddProductActivity : AppCompatActivity() {
    private lateinit var addProductName: TextInputEditText
    private lateinit var addProductPrice: TextInputEditText
    private lateinit var addProductAmount: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product_layout)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addProduct()
        }
        addProductName = findViewById(R.id.add_product_name)
        addProductPrice = findViewById(R.id.add_product_price)
        addProductAmount = findViewById(R.id.add_product_amount)
    }

    private fun addProduct() {
        val resultIntent = Intent()

        if (addProductName.text.isNullOrEmpty() || addProductPrice.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = addProductName.text.toString()
            val price = addProductPrice.text.toString().toInt()
            val amount = addProductAmount.text.toString().toInt()
            resultIntent.putExtra(PRODUCT_NAME, name)
            resultIntent.putExtra(PRODUCT_PRICE, price)
            resultIntent.putExtra(PRODUCT_AMOUNT, amount)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}