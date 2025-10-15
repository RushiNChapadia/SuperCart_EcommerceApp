package com.example.ecommerceapp

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showMessage(
    title: String,
    message: String
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .create()
        .show()
}