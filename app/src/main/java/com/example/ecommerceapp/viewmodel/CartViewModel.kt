package com.example.ecommerceapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.AppDatabase
import com.example.ecommerceapp.model.CartItem
import com.example.ecommerceapp.repository.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CartRepository
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    init {
        val dao = AppDatabase.getDatabase(application).cartDao()
        repository = CartRepository(dao)
        loadCart()
    }

    fun loadCart() {
        viewModelScope.launch {
            val items = repository.getCartItems()
            android.util.Log.d("CartDebug", "🧾 Loaded ${items.size} items from DB: $items")
            _cartItems.value = items
        }
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            repository.addToCart(item)
            loadCart()
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            repository.removeItem(productId)
            loadCart()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
            _cartItems.value = emptyList() // only when actual order is confirmed
        }
    }
}