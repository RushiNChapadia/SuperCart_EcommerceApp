package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.databinding.ActivityCartBinding
import com.example.ecommerceapp.model.CartItem
import com.example.ecommerceapp.viewmodel.CartViewModel

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val cartViewModel: CartViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { finish() }

        setupRecyler()
        observeCart()
        setupCheckout()
    }

    private fun setupCheckout() {
        binding.btnCheckout.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)

            adapter.notifyDataSetChanged()
            binding.tvTotalAmount.text = "$ 0"
            android.widget.Toast.makeText(this, "Order placed successfully!", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeCart() {
        cartViewModel.cartItems.observe(this) { items ->
            adapter = CartAdapter(items.toMutableList(), ::onQuantityChanged, ::onItemRemoved)
            binding.recyclerCart.adapter = adapter
            updateTotal(items)
        }
    }

    private fun setupRecyler() {
        binding.recyclerCart.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(mutableListOf(), ::onQuantityChanged, ::onItemRemoved)
        binding.recyclerCart.adapter = adapter

    }
    private fun updateTotal(items: List<CartItem>) {
        val total = items.sumOf { it.price * it.quantity }
        binding.tvTotalAmount.text = "$ $total"
    }
    private fun onQuantityChanged(item: CartItem) {
        cartViewModel.addToCart(item)
    }
    private fun onItemRemoved(item: CartItem) {
        cartViewModel.removeFromCart(item.productId)
    }
}