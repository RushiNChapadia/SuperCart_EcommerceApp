package com.example.ecommerceapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.databinding.ActivityOrderDetailsBinding
import com.example.ecommerceapp.remote.ApiService
import kotlinx.coroutines.launch

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailsBinding
    private val api = ApiService.getInstance()
    private var orderId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Order Confirmed"

        orderId = intent.getIntExtra("order_id", -1)
        if (orderId == -1) {
            Toast.makeText(this, "Invalid order ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.recyclerViewOrderItems.layoutManager = LinearLayoutManager(this)
        loadOrderDetails()

        binding.btnCancelOrder.setOnClickListener {
            cancelOrder()
        }
    }

    private fun loadOrderDetails() {
        lifecycleScope.launch {
            try {
                val response = api.getOrderDetails(orderId)
                if (response.isSuccessful && response.body() != null) {
                    val order = response.body()!!.order
                    if (order != null) {
                        binding.tvOrderId.text = "Order ID: #${order.order_id}"
                        binding.tvOrderStatus.text = "Order status: ${order.order_status}"
                        binding.tvBillAmount.text = "$ ${order.bill_amount}"
                        binding.tvDeliveryAddress.text = "${order.address_title}\n${order.address}"
                        binding.recyclerViewOrderItems.adapter = OrderItemAdapter(order.items)
                    } else {
                        Toast.makeText(this@OrderDetailsActivity, "Order not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@OrderDetailsActivity, "Failed to load order", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@OrderDetailsActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cancelOrder() {
        Toast.makeText(this, "Order cancellation requested ", Toast.LENGTH_SHORT).show()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}