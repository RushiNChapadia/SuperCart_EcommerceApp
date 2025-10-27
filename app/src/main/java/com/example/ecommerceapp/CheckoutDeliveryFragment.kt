package com.example.ecommerceapp


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.databinding.FragmentCheckoutDeliveryBinding
import com.example.ecommerceapp.model.AddressModel
import com.example.ecommerceapp.remote.ApiService
import kotlinx.coroutines.launch
import com.example.ecommerceapp.databinding.DialogAddAddressBinding
import com.example.ecommerceapp.model.AddAddressRequest
import com.example.ecommerceapp.viewmodel.CheckoutViewModel


class CheckoutDeliveryFragment : Fragment() {
    private val checkoutViewModel: CheckoutViewModel by activityViewModels()

    private lateinit var binding: FragmentCheckoutDeliveryBinding
    private lateinit var adapter: DeliveryAddressAdapter
    private val api = ApiService.getInstance()
    private var selectedAddress: AddressModel? = null
    private val userId: Int by lazy {
        val prefs = requireContext().getSharedPreferences(Constants.SETTINGS, AppCompatActivity.MODE_PRIVATE)
        prefs.getString(Constants.USER_ID, null)?.toIntOrNull() ?: -1
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutDeliveryBinding.inflate(inflater, container, false)

        setupRecyclerView()
        fetchAddresses()

        binding.btnAddAddress.setOnClickListener { showAddAddressDialog() }
        binding.btnNext.setOnClickListener {
            if (selectedAddress == null) {
                Toast.makeText(requireContext(), "Please select a delivery address", Toast.LENGTH_SHORT).show()
            } else {
                checkoutViewModel.setSelectedAddress(selectedAddress!!) // ✅ Save to ViewModel
                (activity as? CheckoutActivity)?.goToNextTab()
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = DeliveryAddressAdapter(emptyList<AddressModel>()) { selected ->
            selectedAddress = selected
        }
        binding.recyclerViewAddresses.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAddresses.adapter = adapter
    }

    private fun fetchAddresses() {
        lifecycleScope.launch {
            try {
                val response = api.getAddresses(userId)
                if (response.isSuccessful && response.body() != null) {
                    adapter.updateList(response.body()!!.addresses)
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAddAddressDialog() {
        val dialogBinding = DialogAddAddressBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnCancel.setOnClickListener { dialog.dismiss() }
        dialogBinding.btnSave.setOnClickListener {
            val title = dialogBinding.etTitle.text.toString()
            val address = dialogBinding.etAddress.text.toString()
            if (title.isNotEmpty() && address.isNotEmpty()) {
                addAddress(title, address)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun addAddress(title: String, address: String) {
        lifecycleScope.launch {
            try {
                val request = AddAddressRequest(
                    user_id = userId,
                    title = title,
                    address = address
                )
                Log.d("AddressDebug", "📡 Sending: $request")
                val response = api.addAddress(request)

                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(requireContext(), " Address added!", Toast.LENGTH_SHORT).show()
                    fetchAddresses()
                } else {
                    Toast.makeText(requireContext(), " Failed to add address", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("AddressDebug", "Error: ${e.message}", e)
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}