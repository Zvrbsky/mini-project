package com.example.mini_project.productDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mini_project.data.DataSource
import com.example.mini_project.data.Product

class ProductDetailViewModel(private val datasource: DataSource) : ViewModel() {

    fun getProductForId(id: Long) : Product? {
        return datasource.getProductForId(id)
    }

    fun removeProduct(product: Product) {
        datasource.removeProduct(product)
    }
    fun addProduct(product : Product)
    {
        datasource.addProduct(product)
    }

    fun updateProduct(oldProduct: Product, newProduct : Product) {
        datasource.addProduct(newProduct)
//        datasource.removeProduct(oldProduct)
    }
}

class ProductDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(
                datasource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}