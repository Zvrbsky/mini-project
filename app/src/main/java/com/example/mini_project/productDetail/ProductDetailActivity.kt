package com.example.mini_project.productDetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_project.ProductApplication
import com.example.mini_project.R
import com.example.mini_project.addProduct.PRODUCT_AMOUNT
import com.example.mini_project.addProduct.PRODUCT_NAME
import com.example.mini_project.addProduct.PRODUCT_PRICE
import com.example.mini_project.db.ProductViewModel
import com.example.mini_project.db.ProductViewModelFactory
import com.example.mini_project.productList.PRODUCT_ID

class ProductDetailActivity : AppCompatActivity() {

    private val productDetailViewModel by viewModels<ProductDetailViewModel> {
        ProductDetailViewModelFactory()
    }

    private val productsViewModel by viewModels<ProductViewModel> {
        ProductViewModelFactory((application as ProductApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail_activity)

        var currentProductId: Long? = null

        val productName: TextView = findViewById(R.id.product_detail_name)
        val productPrice: TextView = findViewById(R.id.product_detail_price)
        val productAmount: TextView = findViewById(R.id.product_detail_amount)
        val updateProductButton: Button = findViewById(R.id.update_button)
        val removeProductButton: Button = findViewById(R.id.remove_button)
        val checkBox : CheckBox = findViewById(R.id.checkBox)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentProductId = bundle.getLong(PRODUCT_ID)
        }

        currentProductId?.let {
            val currentProduct = productDetailViewModel.getProductForId(it)
            productName.text = currentProduct?.name
            productPrice.text = currentProduct?.price.toString()
            productAmount.text = currentProduct?.amount.toString()
            checkBox.isChecked = currentProduct?.isBought!!


            updateProductButton.setOnClickListener {
                if (currentProduct != null) {
                    var newProduct = currentProduct.copy()
                    newProduct.name = productName.text.toString()
                    newProduct.price = productPrice.text.toString().toInt()
                    newProduct.amount = productAmount.text.toString().toInt()
                    newProduct.isBought = checkBox.isChecked
                    productDetailViewModel.updateProduct(newProduct)
                    productsViewModel.update(newProduct)

                }
                finish()
            }

            removeProductButton.setOnClickListener {
                if (currentProduct != null) {
                    productDetailViewModel.removeProduct(currentProduct)
                }
                finish()
            }
        }
    }
}