package com.example.shopping_list_app.data

data class Product (
    val id : Long,
    var name: String,
    var price: Int,
    var amount: Int,
    var isBought: Boolean
)