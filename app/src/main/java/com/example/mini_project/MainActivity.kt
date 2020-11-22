package com.example.mini_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_project.data.Product
import com.example.mini_project.productList.ProductListActivity
import com.example.mini_project.productList.ProductsListViewModel
import com.example.mini_project.productList.ProductsListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private val PREFS_NAME = "MyPrefsFile"
    private val productsListViewModel by viewModels<ProductsListViewModel> {
        ProductsListViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun GoToProductListOnClick(view: View) {
        val intent = Intent(this, ProductListActivity::class.java)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val setOfProducts =  mutableSetOf<String>()
        productsListViewModel.productsLiveData.value?.forEach { product: Product -> setOfProducts.add(Gson().toJson(product)) }
        val editor = settings.edit()
        editor.clear()
        editor.putStringSet("Data", setOfProducts)
        editor.commit()
    }

    override fun onResume() {
        super.onResume()
        val settings = getSharedPreferences(PREFS_NAME, 0)
        settings.getStringSet("Data", mutableSetOf())?.forEach{ product: String ->
            productsListViewModel.insertProduct(Gson().fromJson(product, Product::class.java))
        }
    }
}