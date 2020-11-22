package com.example.mini_project.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {
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

    fun removeProduct(product: Product) {
        val currentList = productsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(product)
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

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}