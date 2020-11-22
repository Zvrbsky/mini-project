package com.example.mini_project

import android.app.Application
import com.example.mini_project.db.ProductRepository
import com.example.mini_project.db.ProductRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProductApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ProductRoomDatabase.getDatabase(this) }
    val repository by lazy { ProductRepository(database.productDao()) }
}