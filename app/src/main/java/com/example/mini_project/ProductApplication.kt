package com.example.mini_project

import android.app.Application
import com.example.mini_project.db.ProductRepository
import com.example.mini_project.db.ProductRoomDatabase

class ProductApplication : Application() {
    val database by lazy { ProductRoomDatabase.getDatabase(this) }
    val repository by lazy { ProductRepository(database.productDao()) }
}