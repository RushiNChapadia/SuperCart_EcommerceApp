package com.example.ecommerceapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.databinding.ItemImageSliderBinding
import com.example.ecommerceapp.model.ProductImage

class ImageSliderAdapter(
    private val context: Context,
    private val images: List<ProductImage>
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val binding: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageSliderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        val imageUrl = "http://10.0.2.2/myshop/images/${image.image}"

        Glide.with(context)
            .load(imageUrl)
            .into(holder.binding.ivSliderImage)
    }

    override fun getItemCount(): Int = images.size
}