package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.databinding.ActivityDashboardBinding
import com.example.ecommerceapp.model.CategoryResponse
import com.example.ecommerceapp.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.ecommerceapp.SmartPhonesActivity
import com.example.ecommerceapp.viewmodel.LogoutViewModel

class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    val apiService: ApiService = ApiService.getInstance()
    private lateinit var adapter: CategoryAdapter
    private val logoutViewModel: LogoutViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logoutViewModel.logoutResponse.observe(this, Observer { response ->
            if (response.isSuccessful && response.body()?.status == 0) {
                Toast.makeText(this, response.body()?.message, Toast.LENGTH_SHORT).show()

                // Clear stored session (if using SharedPreferences)
                val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
                prefs.edit().clear().apply()

                // Redirect to login
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Logout failed: ${response.body()?.message ?: "Try again"}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    performLogout()
                }
                // Add other menu items if needed
            }
            binding.drawerLayout.closeDrawers()
            true
        }



        binding.recyclerViewCategories.layoutManager = GridLayoutManager(this, 2)


        fetchCategories()
    }

    private fun fetchCategories() {
        apiService.getCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val categoryResponse = response.body()
                    if (categoryResponse?.status == 0) {
                        adapter = CategoryAdapter(
                            categories = categoryResponse.categories
                        ) { category ->
                            val id = category.categoryId
                            if (id == "1" || id == 1.toString()) {
                                val intent =
                                    Intent(this@DashboardActivity, SmartPhonesActivity::class.java)
                                intent.putExtra("category_id", id.toString().toInt())
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this@DashboardActivity,
                                    "Coming soon: ${category.categoryName}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        binding.recyclerViewCategories.adapter = adapter
                    } else {
                        Toast.makeText(
                            this@DashboardActivity,
                            categoryResponse?.message ?: "Failed to load categories",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@DashboardActivity,
                        "Error loading categories",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Toast.makeText(
                    this@DashboardActivity,
                    "Network Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun performLogout() {
        val prefs = getSharedPreferences(Constants.SETTINGS, MODE_PRIVATE)
        val email = prefs.getString(Constants.EMAIL_ID, null)

        if (email != null) {
            logoutViewModel.logout(email)
        } else {
            Toast.makeText(this, "No email found in session", Toast.LENGTH_SHORT).show()
        }
    }

}