package com.example.mini_project.productList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_project.R
import com.example.mini_project.addProduct.AddProductActivity
import com.example.mini_project.addProduct.PRODUCT_NAME
import com.example.mini_project.addProduct.PRODUCT_PRICE
import com.example.mini_project.data.Product
import com.example.mini_project.productDetail.ProductDetailActivity

const val PRODUCT_ID = "product id"

class ProductListActivity : AppCompatActivity() {
    private val newProductActivityRequestCode = 1
    private val productsListViewModel by viewModels<ProductsListViewModel> {
        ProductsListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val productAdapter = ProductAdapter { product -> adapterOnClick(product) }
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = productAdapter

        productsListViewModel.productsLiveData.observe(this, {
            it?.let {
                productAdapter.submitList(it as MutableList<Product>)
            }
        })

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    private fun adapterOnClick(product: Product) {
        val intent = Intent(this, ProductDetailActivity()::class.java)
        intent.putExtra(PRODUCT_ID, product.id)
        startActivity(intent)
    }

    private fun fabOnClick() {
        val intent = Intent(this, AddProductActivity::class.java)
        startActivityForResult(intent, newProductActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newProductActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val productName = data.getStringExtra(PRODUCT_NAME)
                val productPrice = data.getIntExtra(PRODUCT_PRICE, 0)
                val productAmount = data.getIntExtra(PRODUCT_PRICE, 0)

                productsListViewModel.insertProduct(productName, productPrice, productAmount, false)
            }
        }
    }
}