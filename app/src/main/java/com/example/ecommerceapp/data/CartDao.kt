package com.example.ecommerceapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerceapp.model.CartItem

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: CartItem)

    @Query("SELECT * FROM cart_items")
    suspend fun getAllItems(): List<CartItem>

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getItemById(productId: String): CartItem?

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteItem(productId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Update
    suspend fun updateItem(item: CartItem)
}