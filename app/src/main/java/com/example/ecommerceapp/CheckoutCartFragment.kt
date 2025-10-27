package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.databinding.FragmentCheckoutCartBinding
import com.example.ecommerceapp.viewmodel.CartViewModel

class CheckoutCartFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutCartBinding
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutCartBinding.inflate(inflater, container, false)

        // ✅ Ensure we load cart data from Room DB
        cartViewModel.loadCart()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // ✅ Observe LiveData for changes
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            val adapter = CheckoutCartAdapter(items)
            binding.recyclerView.adapter = adapter

            val total = items.sumOf { it.price * it.quantity }
            binding.tvTotal.text = "$ $total"
        }

        // ✅ On "Next" click, do NOT clear the cart
        binding.btnNext.setOnClickListener {
            val totalAmount = binding.tvTotal.text
            Toast.makeText(
                requireContext(),
                "Order placed ✅ Total: $totalAmount",
                Toast.LENGTH_SHORT
            ).show()

            // ✅ Proceed to delivery tab
            (activity as? CheckoutActivity)?.goToNextTab()
        }

        return binding.root
    }
}