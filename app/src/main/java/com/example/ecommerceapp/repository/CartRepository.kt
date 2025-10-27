package com.example.ecommerceapp.repository

import com.example.ecommerceapp.data.CartDao
import com.example.ecommerceapp.model.CartItem

class CartRepository(private val cartDao: CartDao) {
    suspend fun addToCart(item: CartItem)=cartDao.addItem(item)
    suspend fun getCartItems()=cartDao.getAllItems()
    suspend fun updateItem(item: CartItem)=cartDao.updateItem(item)
    suspend fun removeItem(productId: String)=cartDao.deleteItem(productId)
    suspend fun clearCart()=cartDao.clearCart()
}
