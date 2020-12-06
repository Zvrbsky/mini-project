package com.example.shopping_list_app.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shopping_list_app.data.Product
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    fun insert(product: Product) = viewModelScope.launch {
        val productEntity = ProductEntity(product.id, product.name, product.price, product.amount, product.isBought)
        repository.insert(productEntity)
    }

    fun update(product: Product) = viewModelScope.launch {
        val productEntity = ProductEntity(product.id, product.name, product.price, product.amount, product.isBought)
        repository.update(productEntity)
    }

    fun delete(product: Product) = viewModelScope.launch {
        val productEntity = ProductEntity(product.id, product.name, product.price, product.amount, product.isBought)
        repository.delete(productEntity)
    }
}

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}