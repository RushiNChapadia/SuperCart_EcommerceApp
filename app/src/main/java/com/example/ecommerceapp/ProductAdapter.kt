package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.model.ProductModel
import com.example.ecommerceapp.databinding.ItemProductBinding

class ProductAdapter(
    private val products: List<ProductModel>,
    private val onItemClick: (ProductModel) -> Unit // callback for item click
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val BASE_IMAGE_URL = "http://10.0.2.2/myshop/images/"

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        val b = holder.binding

        // Bind product info
        b.tvName.text = product.product_name
        b.tvDesc.text = product.description
        b.tvPrice.text = "$ ${product.price}"

        Glide.with(b.imgProduct.context)
            .load(BASE_IMAGE_URL + product.product_image_url)
            .into(b.imgProduct)

        // 🟢 Handle clicks on the entire item (image, name, desc, etc.)
        b.root.setOnClickListener {
            onItemClick(product)
        }

        // 🛒 Add-to-cart logic
        b.btnAddToCart.setOnClickListener {
            b.btnAddToCart.visibility = View.GONE
            b.layoutQuantity.visibility = View.VISIBLE
            b.tvQuantity.text = "1"
        }

        b.btnPlus.setOnClickListener {
            var qty = b.tvQuantity.text.toString().toInt()
            b.tvQuantity.text = (++qty).toString()
        }

        b.btnMinus.setOnClickListener {
            var qty = b.tvQuantity.text.toString().toInt()
            if (qty > 1) {
                b.tvQuantity.text = (--qty).toString()
            } else {
                b.layoutQuantity.visibility = View.GONE
                b.btnAddToCart.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = products.size
}


//class ProductAdapter(private val products: List<ProductModel>) :
//    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
//
//    private val BASE_IMAGE_URL = "http://10.0.2.2/myshop/images/"
//
//    inner class ProductViewHolder(val binding: ItemProductBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
//        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ProductViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
//        val product = products[position]
//        val b = holder.binding
//
//        b.tvName.text = product.product_name
//        b.tvDesc.text = product.description
//        b.tvPrice.text = "$ ${product.price}"
//
//        Glide.with(b.imgProduct.context)
//            .load(BASE_IMAGE_URL + product.product_image_url)
//            .into(b.imgProduct)
//
//        // Add-to-cart logic
//        b.btnAddToCart.setOnClickListener {
//            b.btnAddToCart.visibility = View.GONE
//            b.layoutQuantity.visibility = View.VISIBLE
//            b.tvQuantity.text = "1"
//        }
//
//        b.btnPlus.setOnClickListener {
//            var qty = b.tvQuantity.text.toString().toInt()
//            b.tvQuantity.text = (++qty).toString()
//        }
//
//        b.btnMinus.setOnClickListener {
//            var qty = b.tvQuantity.text.toString().toInt()
//            if (qty > 1) {
//                b.tvQuantity.text = (--qty).toString()
//            } else {
//                b.layoutQuantity.visibility = View.GONE
//                b.btnAddToCart.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    override fun getItemCount(): Int = products.size
//}

