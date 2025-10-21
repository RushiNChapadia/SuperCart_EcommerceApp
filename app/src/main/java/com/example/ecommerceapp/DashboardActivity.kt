package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.databinding.ActivityDashboardBinding
import com.example.ecommerceapp.model.CategoryResponse
import com.example.ecommerceapp.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.ecommerceapp.SmartPhonesActivity

class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    val apiService: ApiService = ApiService.getInstance()
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


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
}