package com.example.ecommerceapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ecommerceapp.databinding.ActivityProductDetailsBinding
import com.example.ecommerceapp.viewmodel.ProductDetailsViewModel
import com.example.ecommerceapp.ReviewAdapter
import com.example.ecommerceapp.SpecificationAdapter
import com.example.ecommerceapp.ImageSliderAdapter
import com.example.ecommerceapp.model.CartItem
import com.example.ecommerceapp.viewmodel.CartViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel: ProductDetailsViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Get product_id as String and convert safely
        val productIdStr = intent.getStringExtra("product_id")
        val productId = productIdStr?.toIntOrNull()

        if (productId == null) {
            Toast.makeText(this, "Invalid or missing product ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupRecyclerViews()
        observeData()

        //  Trigger ViewModel fetch AFTER observers are set
        viewModel.fetchProductDetails(productId)

        //  Handle Add to Cart button
        binding.btnAddToCart.setOnClickListener {
            val product = viewModel.productDetailsResponse.value?.body()?.product
            if (product != null) {
                val item = CartItem(
                    productId = product.product_id,
                    productName = product.product_name,
                    description = product.description,
                    product_image_url = product.product_image_url,
                    price = product.price.toDoubleOrNull() ?: 0.0,
                    quantity = 1
                )

                lifecycleScope.launch {
                    cartViewModel.addToCart(item)
                    android.util.Log.d("CartDebug", "✅ Added to cart: $item")
                    Toast.makeText(this@ProductDetailsActivity, "Added to cart", Toast.LENGTH_SHORT).show()
                }
            }
        }

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


                //  Set Adapters (make sure adapters exist)
                binding.viewPagerImages.adapter = ImageSliderAdapter(this, product.images)
                binding.rvSpecifications.adapter = SpecificationAdapter(product.specifications)
                binding.rvReviews.adapter = ReviewAdapter(product.reviews)
            } else {
                Toast.makeText(this, "Failed to load product details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}