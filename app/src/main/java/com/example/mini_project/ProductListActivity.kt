package com.example.mini_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        setSupportActionBar(findViewById(R.id.toolbar))


    }
}