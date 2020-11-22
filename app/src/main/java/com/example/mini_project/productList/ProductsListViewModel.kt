package com.example.mini_project.productList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mini_project.data.DataSource
import com.example.mini_project.data.Product
import kotlin.random.Random

class ProductsListViewModel(val dataSource: DataSource) : ViewModel() {

    val productsLiveData = dataSource.getProductList()

    fun insertProduct(productName: String?, price: Int?, amount: Int?, isBought: Boolean?) {
        if (productName == null || price == null || amount == null || isBought == null) {
            return
        }

        val newProduct = Product(
            Random.nextLong(),
            productName,
            price,
            amount,
            isBought
        )

        dataSource.addProduct(newProduct)
    }

    fun insertProduct(product: Product) {
        dataSource.addProduct(product)
    }
}

class ProductsListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductsListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}