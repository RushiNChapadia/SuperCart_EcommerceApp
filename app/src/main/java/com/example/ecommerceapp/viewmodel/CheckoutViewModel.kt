package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.model.AddressModel

class CheckoutViewModel : ViewModel() {

    private val _selectedAddress = MutableLiveData<AddressModel?>()
    val selectedAddress: LiveData<AddressModel?> = _selectedAddress

    private val _selectedPayment = MutableLiveData<String>()
    val selectedPayment: LiveData<String> = _selectedPayment

    fun setSelectedAddress(address: AddressModel) {
        _selectedAddress.value = address
    }

    fun setSelectedPayment(payment: String) {
        _selectedPayment.value = payment
    }
}