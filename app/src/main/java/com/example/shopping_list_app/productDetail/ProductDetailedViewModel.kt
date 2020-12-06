package com.example.shopping_list_app.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopping_list_app.data.DataSource
import com.example.shopping_list_app.data.Product

class ProductDetailViewModel(private val datasource: DataSource) : ViewModel() {

    fun getProductForId(id: Long) : Product? {
        return datasource.getProductForId(id)
    }

    fun removeProduct(product: Product) {
        datasource.removeProduct(product)
    }

    fun updateProduct(product : Product) {
        datasource.updateProduct(product)
    }
}

class ProductDetailViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(
                datasource = DataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}