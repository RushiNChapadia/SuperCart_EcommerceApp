package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapp.databinding.ActivityCheckoutBinding
import com.example.ecommerceapp.viewmodel.CartViewModel
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var adapter: CheckoutPagerAdapter
    private val cartViewModel: CartViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        adapter = CheckoutPagerAdapter(this)
        binding.viewPager.adapter = adapter

        cartViewModel.loadCart()

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Cart Items"
                1 -> "Delivery"
                2 -> "Payment"
                3 -> "Summary"
                else -> ""
            }
        }.attach()
    }
    fun goToNextTab() {
        val nextItem = binding.viewPager.currentItem + 1
        if (nextItem < binding.viewPager.adapter?.itemCount ?: 0) {
            binding.viewPager.currentItem = nextItem
        }
    }

}