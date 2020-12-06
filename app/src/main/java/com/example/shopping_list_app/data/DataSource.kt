package com.example.shopping_list_app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource() {
    private val initialProductList = mutableListOf<Product>()
    private val productsLiveData = MutableLiveData(initialProductList)

    fun addProduct(product: Product) {
        val currentList = productsLiveData.value
        if (currentList == null) {
            productsLiveData.postValue(mutableListOf(product))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, product)
            productsLiveData.postValue(updatedList)
        }
    }

    fun initWithProducts(products: List<Product>) {
        productsLiveData.postValue(products.toMutableList())
    }

    fun removeProduct(product: Product) {
        val currentList = productsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(product)
            productsLiveData.postValue(updatedList)
        }
    }

    fun updateProduct(product: Product) {
        val currentList = productsLiveData.value
        if (currentList != null) {
            val oldProduct = getProductForId(product.id)
            val updatedList = currentList.toMutableList()
            updatedList.remove(oldProduct)
            updatedList.add(0, product)
            productsLiveData.postValue(updatedList)
        }
    }

    fun getProductForId(id: Long): Product? {
        productsLiveData.value?.let { products ->
            return products.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getProductList(): LiveData<MutableList<Product>> {
        return productsLiveData
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}