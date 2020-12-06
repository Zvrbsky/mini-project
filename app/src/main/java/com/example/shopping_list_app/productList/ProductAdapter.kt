package com.example.shopping_list_app.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_list_app.R
import com.example.shopping_list_app.data.Product

class ProductAdapter (private val onClick: (Product) -> Unit):
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback){

    class ProductViewHolder(itemView: View, val onClick: (Product) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.product_name)
        private val priceTextView: TextView = itemView.findViewById(R.id.product_price)
        private val amountTextView: TextView = itemView.findViewById(R.id.product_amount)
        private val isBoughtImageView: ImageView = itemView.findViewById(R.id.product_isBought)
        private var currentProduct: Product? = null

        init {
            itemView.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }
        
        fun bind(product: Product) {
            currentProduct = product

            nameTextView.text = product.name
            priceTextView.text = product.price.toString()
            amountTextView.text = product.amount.toString()
            if (product.isBought)
            {
                isBoughtImageView.setImageResource(android.R.drawable.checkbox_on_background)
            }
            else
            {
                isBoughtImageView.setImageResource(android.R.drawable.checkbox_off_background)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view, onClick)
    }
    
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)

    }

}

object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }
}