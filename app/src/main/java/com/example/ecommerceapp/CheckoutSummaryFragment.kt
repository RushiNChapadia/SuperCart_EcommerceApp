package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.databinding.FragmentCheckoutSummaryBinding
import com.example.ecommerceapp.model.DeliveryAddress
import com.example.ecommerceapp.model.OrderItem
import com.example.ecommerceapp.model.OrderRequest
import com.example.ecommerceapp.remote.ApiService
import com.example.ecommerceapp.viewmodel.CartViewModel
import com.example.ecommerceapp.viewmodel.CheckoutViewModel
import kotlinx.coroutines.launch


class CheckoutSummaryFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutSummaryBinding
    private val cartViewModel: CartViewModel by activityViewModels()
    private val checkoutViewModel: CheckoutViewModel by activityViewModels()
    private val api = ApiService.getInstance()
    private var totalAmount = 0.0
    private var orderPlaced = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutSummaryBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeData()

        binding.btnConfirmOrder.setOnClickListener {
            if (!orderPlaced) placeOrder()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSummaryCart.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            binding.recyclerViewSummaryCart.adapter = CheckoutCartAdapter(items)
            totalAmount = items.sumOf { it.price * it.quantity }
            binding.tvTotalAmount.text = "$ $totalAmount"
        }

        checkoutViewModel.selectedAddress.observe(viewLifecycleOwner) { address ->
            binding.tvDeliveryAddress.text =
                if (address != null) "${address.title}\n${address.address}" else "No address selected"
        }

        checkoutViewModel.selectedPayment.observe(viewLifecycleOwner) { payment ->
            binding.tvPaymentOption.text = payment.ifEmpty { "No payment selected" }
        }
    }

    private fun placeOrder() {
        val userPrefs = requireContext().getSharedPreferences(
            Constants.SETTINGS,
            AppCompatActivity.MODE_PRIVATE
        )


        val userIdStr = userPrefs.getString(Constants.USER_ID, null)
        val userId = userIdStr?.toIntOrNull() ?: -1

        val address = checkoutViewModel.selectedAddress.value
        val paymentMethod = checkoutViewModel.selectedPayment.value ?: "COD"
        val items = cartViewModel.cartItems.value ?: emptyList()

        if (userId == -1 || address == null || items.isEmpty()) {
            Toast.makeText(requireContext(), "Missing checkout information", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val orderItems = items.map {
            OrderItem(
                product_id = it.productId.toIntOrNull() ?: 0,
                quantity = it.quantity,
                unit_price = it.price
            )
        }

        val orderRequest = OrderRequest(
            user_id = userId,
            delivery_address = DeliveryAddress(address.title, address.address),
            items = orderItems,
            bill_amount = totalAmount,
            payment_method = paymentMethod
        )

        lifecycleScope.launch {
            try {
                val response = api.placeOrder(orderRequest)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    if (result.status == 0) {
                        Toast.makeText(requireContext(), " ${result.message}", Toast.LENGTH_SHORT)
                            .show()

                        //  Go to OrderDetailsActivity
                        val intent = Intent(requireContext(), OrderDetailsActivity::class.java)
                        intent.putExtra("order_id", result.order_id)
                        startActivity(intent)

                        // Clear cart & mark order as placed
                        cartViewModel.clearCart()
                        orderPlaced = true
                        requireActivity().finish()
                    } else {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to place order", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
