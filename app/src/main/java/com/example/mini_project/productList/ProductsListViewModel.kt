package com.example.mini_project.productList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mini_project.data.DataSource
import com.example.mini_project.data.Product
import kotlin.random.Random

class ProductsListViewModel(val dataSource: DataSource) : ViewModel() {

    val productsLiveData = dataSource.getProductList()

    fun insertProduct(product: Product) {
        dataSource.addProduct(product)
    }

    fun initWithProducts(products: List<Product>) {
        dataSource.initWithProducts(products)
    }
}

class ProductsListViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductsListViewModel(
                dataSource = DataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}