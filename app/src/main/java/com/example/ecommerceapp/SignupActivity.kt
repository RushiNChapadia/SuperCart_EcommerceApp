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

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    val apiService: ApiService = ApiService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            signup()
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun signup() {
        with(binding) {
            val fullName = binding.etFullName.text.toString()
            val mobileNo = binding.etMobileNo.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmedPassword = binding.etConfirmedPassword.text.toString()
            val signupRequest = SignupRequest(fullName, mobileNo, email, password)

            if (fullName.isEmpty() || mobileNo.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirmedPassword.isEmpty()) {
                Toast.makeText(this@SignupActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return
            }

            if (password != confirmedPassword) {
                Toast.makeText(this@SignupActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return
            }

            val call: Call<SignupResponse> = apiService.signup(signupRequest)

            call.enqueue(object : Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse?>,response: Response<SignupResponse?>) {
                    if(!response.isSuccessful){
                        Toast.makeText(this@SignupActivity, "Signup failed!", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val result = response.body()
                    if (result != null) {
                        Toast.makeText(this@SignupActivity,"Signup successful! Please login.",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                        finish()
                    }
                }

                override fun onFailure(call: Call<SignupResponse?>,t: Throwable) {
                    Toast.makeText(this@SignupActivity, "Network error.", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}