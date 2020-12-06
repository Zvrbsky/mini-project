package com.example.shopping_list_app

import android.app.Application
import com.example.shopping_list_app.db.ProductRepository
import com.example.shopping_list_app.db.ProductRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProductApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ProductRoomDatabase.getDatabase(this) }
    val repository by lazy { ProductRepository(database.productDao()) }
}