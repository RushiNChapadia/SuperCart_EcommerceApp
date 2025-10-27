package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.databinding.ItemOrderProductBinding

import com.example.ecommerceapp.model.OrderItemDetail

class OrderItemAdapter(private val items: List<OrderItemDetail>) :
    RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {

    private val BASE_IMAGE_URL = "http://10.0.2.2/myshop/images/"

    inner class OrderItemViewHolder(val binding: ItemOrderProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding = ItemOrderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val item = items[position]
        val b = holder.binding
        b.tvProductName.text = item.product_name
        b.tvUnitPrice.text = "$ ${item.unit_price}"
        b.tvQuantity.text = item.quantity
        b.tvAmount.text = "$ ${item.amount}"

        Glide.with(b.imgProduct.context)
            .load(BASE_IMAGE_URL + item.product_image_url)
            .into(b.imgProduct)
    }

    override fun getItemCount(): Int = items.size
}
