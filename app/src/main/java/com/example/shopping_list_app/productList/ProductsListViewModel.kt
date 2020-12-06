package com.example.shopping_list_app.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopping_list_app.data.DataSource
import com.example.shopping_list_app.data.Product

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