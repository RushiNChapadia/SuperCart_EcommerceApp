package com.example.ecommerceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ecommerceapp.databinding.FragmentCheckoutPaymentBinding
import com.example.ecommerceapp.viewmodel.CheckoutViewModel

class CheckoutPaymentFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutPaymentBinding
    private val checkoutViewModel: CheckoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutPaymentBinding.inflate(inflater, container, false)

        binding.btnNextPayment.setOnClickListener {
            val selectedOption = when (binding.radioGroupPayment.checkedRadioButtonId) {
                R.id.rbCashOnDelivery -> "Cash On Delivery"
                R.id.rbInternetBanking -> "Internet Banking"
                R.id.rbDebitCredit -> "Debit/Credit Card"
                R.id.rbPaypal -> "PayPal"
                else -> "Unknown"
            }

            checkoutViewModel.setSelectedPayment(selectedOption) // ✅ Save to ViewModel
            Toast.makeText(requireContext(), "Selected: $selectedOption", Toast.LENGTH_SHORT).show()
            (activity as? CheckoutActivity)?.goToNextTab()
        }

        return binding.root
    }
}
