package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.databinding.ItemCheckoutCartBinding
import com.example.ecommerceapp.model.CartItem

class CheckoutCartAdapter(
    private val cartItems: List<CartItem>
) : RecyclerView.Adapter<CheckoutCartAdapter.CartViewHolder>() {

    private val BASE_IMAGE_URL = "http://10.0.2.2/myshop/images/"

    inner class CartViewHolder(val binding: ItemCheckoutCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCheckoutCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        val b = holder.binding

        b.tvProductName.text = item.productName
        b.tvDescription.text = item.description
        b.tvQuantity.text = "Qty: ${item.quantity}"
        b.tvPrice.text = "$ ${item.price * item.quantity}"

        Glide.with(b.ivProduct.context)
            .load(BASE_IMAGE_URL + item.product_image_url)
            .into(b.ivProduct)
    }

    override fun getItemCount(): Int = cartItems.size
}
