package com.example.mini_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_project.addProduct.AddProductActivity
import com.example.mini_project.data.Product
import com.example.mini_project.productList.ProductListActivity
import com.example.mini_project.productList.ProductsListViewModel
import com.example.mini_project.productList.ProductsListViewModelFactory
import com.example.mini_project.settings.SettingActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private val PREFS_NAME = "MyPrefsFile"
    private val productsListViewModel by viewModels<ProductsListViewModel> {
        ProductsListViewModelFactory()
    }
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
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val setOfProducts =  mutableSetOf<String>()
        productsListViewModel.productsLiveData.value?.forEach { product: Product -> setOfProducts.add(Gson().toJson(product)) }
        val editor = settings.edit()
        editor.clear()
        editor.putStringSet("Data", setOfProducts)
        editor.commit()
    }
}