package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecommerceapp.databinding.ActivityLoginBinding
import com.example.ecommerceapp.model.LoginRequest
import com.example.ecommerceapp.model.LoginResponse
import com.example.ecommerceapp.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val apiService: ApiService = ApiService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
    }

    private fun login() {
        with(binding){
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val loginRequest = LoginRequest(email, password)

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return
            }

            val call: Call<LoginResponse> = apiService.login(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                    return
                    // Proceed to the next screen or main activity
                }

                val result = response.body()
                if(result!=null){
                    val preference=getSharedPreferences(Constants.SETTINGS, MODE_PRIVATE)
                    preference.edit().apply{
                        putString(Constants.USER_ID,result.user?.userId)
                        putString(Constants.FULL_NAME,result.user?.fullName)
                        putString(Constants.EMAIL_ID,result.user?.emailId)
                        putString(Constants.MOBILE_NO,result.user?.mobileNo)
                        putBoolean(Constants.LOGGED_IN,true)
                        apply()
                    }

                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    finish()
                    return
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Network error.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
}