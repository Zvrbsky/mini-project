package com.example.mini_project.data

data class Product (
    val id : Long,
    var name: String,
    var price: Int,
    var amount: Int,
    var isBought: Boolean
)