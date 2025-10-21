package com.example.ecommerceapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.databinding.ItemCategoryBinding
import com.example.ecommerceapp.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val BASE_IMAGE_URL = "http://10.0.2.2/myshop/images/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        // NOTE: use your actual property names from Category.kt
        holder.binding.tvCategoryName.text = category.categoryName

        val imageUrl = if (category.categoryImageUrl.startsWith("http")) {
            category.categoryImageUrl
        } else {
            BASE_IMAGE_URL + category.categoryImageUrl
        }

        Glide.with(holder.binding.ivCategoryImage.context)
            .load(imageUrl)
            .into(holder.binding.ivCategoryImage)

        // 🔹 Click
        holder.binding.root.setOnClickListener {
            onItemClick(category)
        }
    }

    override fun getItemCount(): Int = categories.size
}

