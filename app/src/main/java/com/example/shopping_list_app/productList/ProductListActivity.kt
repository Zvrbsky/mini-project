package com.example.shopping_list_app.productList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_list_app.ProductApplication
import com.example.shopping_list_app.R
import com.example.shopping_list_app.addProduct.AddProductActivity
import com.example.shopping_list_app.addProduct.PRODUCT_NAME
import com.example.shopping_list_app.addProduct.PRODUCT_PRICE
import com.example.shopping_list_app.data.Product
import com.example.shopping_list_app.db.ProductViewModel
import com.example.shopping_list_app.db.ProductViewModelFactory
import com.example.shopping_list_app.productDetail.ProductDetailActivity
import kotlin.random.Random

const val PRODUCT_ID = "product id"
const val NEW_PRODUCT_ADDED_BROADCAST = "SHOPPING_LIST_APP.NEW_PRODUCT_ADDED_BROADCAST"

class ProductListActivity : AppCompatActivity() {
    private val newProductActivityRequestCode = 1
    private val productsListViewModel by viewModels<ProductsListViewModel> {
        ProductsListViewModelFactory()
    }
    private val productsViewModel by viewModels<ProductViewModel> {
        ProductViewModelFactory((application as ProductApplication).repository)
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
                val productId = Random.nextLong()

                if (productName != null){
                    val product = Product(
                        productId,
                        productName,
                        productPrice,
                        productAmount,
                        false)

                    productsListViewModel.insertProduct(product)
                    productsViewModel.insert(product)

                    val broadcastIntent = Intent(NEW_PRODUCT_ADDED_BROADCAST)
                    broadcastIntent.putExtra(PRODUCT_NAME, productName)

                    Log.d("ProductListActivity", "Broadcast sent for product with id: $productId")
                    broadcastIntent.putExtra(PRODUCT_ID, productId)

                    sendBroadcast(broadcastIntent)
                }
            }
        }
    }
}