package com.example.ecommerceapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ecommerceapp.databinding.ActivityProductDetailsBinding
import com.example.ecommerceapp.viewmodel.ProductDetailsViewModel
import com.example.ecommerceapp.ReviewAdapter
import com.example.ecommerceapp.SpecificationAdapter
import com.example.ecommerceapp.ImageSliderAdapter

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel: ProductDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Get product_id as String and convert safely
        val productIdStr = intent.getStringExtra("product_id")
        val productId = productIdStr?.toIntOrNull()

        if (productId == null) {
            Toast.makeText(this, "Invalid or missing product ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupRecyclerViews()
        observeData()

        // ✅ Trigger ViewModel fetch AFTER observers are set
        viewModel.fetchProductDetails(productId)
    }

    private fun setupRecyclerViews() {
        binding.rvSpecifications.layoutManager = LinearLayoutManager(this)
        binding.rvReviews.layoutManager = LinearLayoutManager(this)
        binding.viewPagerImages.adapter = ImageSliderAdapter(this, emptyList())
        binding.rvSpecifications.adapter = SpecificationAdapter(emptyList())
        binding.rvReviews.adapter = ReviewAdapter(emptyList())


    }

    private fun observeData() {
        viewModel.productDetailsResponse.observe(this) { response ->
            if (response.isSuccessful && response.body()?.status == 0) {
                val product = response.body()!!.product

                binding.tvProductName.text = product.product_name
                binding.tvDescription.text = product.description
                binding.tvPrice.text = "$ ${product.price}"


                // ✅ Set Adapters (make sure adapters exist)
                binding.viewPagerImages.adapter = ImageSliderAdapter(this, product.images)
                binding.rvSpecifications.adapter = SpecificationAdapter(product.specifications)
                binding.rvReviews.adapter = ReviewAdapter(product.reviews)
            } else {
                Toast.makeText(this, "Failed to load product details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}