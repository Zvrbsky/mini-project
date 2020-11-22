package com.example.mini_project.settings

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_project.R
import com.example.mini_project.productList.ProductsListViewModel
import com.example.mini_project.productList.ProductsListViewModelFactory


class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

}