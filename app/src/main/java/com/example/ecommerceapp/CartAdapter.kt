package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.databinding.ItemCartBinding
import com.example.ecommerceapp.model.CartItem

class CartAdapter (
    private val items: MutableList<CartItem>,
    private val onQuantityChanged: (CartItem) -> Unit,
    private val onItemRemoved: (CartItem) -> Unit
): RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvName.text = item.productName
            tvDesc.text = item.description
            tvUnitPrice.text = "$ ${item.price}"
            tvTotalPrice.text = "$ ${item.price * item.quantity}"
            tvQuantity.text = item.quantity.toString()

            Glide.with(imgProduct.context)
                .load("http://10.0.2.2/myshop/images/${item.product_image_url}")
                .into(imgProduct)

            btnPlus.setOnClickListener {
                val newItem = item.copy(quantity = item.quantity + 1)
                items[position] = newItem
                onQuantityChanged(newItem)
                notifyItemChanged(position)
            }
            btnMinus.setOnClickListener {
                if (item.quantity > 1) {
                    val newItem = item.copy(quantity = item.quantity - 1)
                    items[position] = newItem
                    onQuantityChanged(newItem)
                    notifyItemChanged(position)
                } else {
                    onItemRemoved(item)
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}