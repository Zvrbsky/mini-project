package com.example.shopping_list_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shopping_list_app.data.Product
import com.example.shopping_list_app.productDetail.ProductDetailActivity
import com.example.shopping_list_app.productList.PRODUCT_ID
import com.example.shopping_list_app.productList.ProductListActivity
import com.example.shopping_list_app.productList.ProductsListViewModel
import com.example.shopping_list_app.productList.ProductsListViewModelFactory
import com.example.shopping_list_app.settings.SettingActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


const val SHOW_PRODUCT_DETAILS_BROADCAST = "NOTIFICATION_APP.SHOW_PRODUCT_DETAILS_BROADCAST"

class MainActivity : AppCompatActivity() {
    private val PREFS_NAME = "MyPrefsFile"
    private val productsListViewModel by viewModels<ProductsListViewModel> {
        ProductsListViewModelFactory()
    }
    lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }

        val settings = getSharedPreferences(PREFS_NAME, 0)
        val setOfProducts = settings.getStringSet("Data", mutableSetOf())
        val listOfProducts = mutableListOf<Product>()
        setOfProducts?.forEach{ product: String ->
            listOfProducts.add(Gson().fromJson(product, Product::class.java))
        }
        productsListViewModel.initWithProducts(listOfProducts)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val respIntent =  Intent(context, ProductDetailActivity::class.java)
                val productId =  intent?.getLongExtra(PRODUCT_ID, 0)
                Log.d("MainActivity", "Broadcast received for product with id: $productId")
                respIntent.putExtra(PRODUCT_ID,  productId)
                startActivity(respIntent)
            }
        }
        registerReceiver(receiver,
                IntentFilter(SHOW_PRODUCT_DETAILS_BROADCAST))
    }

    fun goToProductListOnClick(view: View) {
        val intent = Intent(this, ProductListActivity::class.java)
        startActivity(intent)
    }

    private fun fabOnClick() {
        val intent =  Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val setOfProducts =  mutableSetOf<String>()
        productsListViewModel.productsLiveData.value?.forEach { product: Product -> setOfProducts.add(Gson().toJson(product)) }
        val editor = settings.edit()
        editor.clear()
        editor.putStringSet("Data", setOfProducts)
        editor.commit()
    }
}