package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecommerceapp.databinding.ActivitySignupBinding
import com.example.ecommerceapp.model.SignupRequest
import com.example.ecommerceapp.model.SignupResponse
import com.example.ecommerceapp.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.ecommerceapp.viewmodel.SignupViewModel
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener { validateAndSignup() }
        binding.btnLogin.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        observeViewModel()
    }

    private fun validateAndSignup() {
        val fullName = binding.etFullName.text.toString().trim()
        val mobileNo = binding.etMobileNo.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmedPassword = binding.etConfirmedPassword.text.toString().trim()

        if (fullName.isEmpty() || mobileNo.isEmpty() || email.isEmpty()
            || password.isEmpty() || confirmedPassword.isEmpty()
        ) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmedPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        val signupRequest = SignupRequest(fullName, mobileNo, email, password)

        viewModel.registerUser(signupRequest)
    }

    private fun observeViewModel() {
        viewModel.signupResponse.observe(this, Observer {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            if (it.status == 0) {
                val email = binding.etEmail.text.toString()
                val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
                prefs.edit().putString("email", email).apply()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}
