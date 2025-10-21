package com.example.ecommerceapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerceapp.retrofit.RetrofitClient
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import com.example.ecommerceapp.ViewPagerAdapter
import com.example.ecommerceapp.ProductFragment

class SmartPhonesActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_phones)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        fetchSubCategories(1)
    }

    private fun fetchSubCategories(categoryId: Int) {
        lifecycleScope.launch {
            try {

                Toast.makeText(this@SmartPhonesActivity, "Fetching subcategories…", Toast.LENGTH_SHORT).show()

                val response = RetrofitClient.instance.getSubCategories(categoryId)


                android.util.Log.d("SmartPhonesActivity", "URL = ${response.raw().request.url}")
                android.util.Log.d("SmartPhonesActivity", "Code = ${response.code()}")
                android.util.Log.d("SmartPhonesActivity", "Body = ${response.body()}")

                val body = response.body()
                if (response.isSuccessful && body != null && body.status == 0) {
                    val subcategories = body.subcategories
                    android.util.Log.d("SmartPhonesActivity", "Subcategories = ${subcategories.size}")

                    if (subcategories.isNotEmpty()) {
                        for (sub in subcategories) {
                            adapter.addFragment(
                                ProductFragment.newInstance(sub.subcategory_id),
                                sub.subcategory_name
                            )
                        }
                        viewPager.adapter = adapter
                        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                            tab.text = adapter.getTitle(position)
                        }.attach()
                    } else {
                        Toast.makeText(
                            this@SmartPhonesActivity,
                            "Empty list returned from API",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        this@SmartPhonesActivity,
                        "No subcategories found (status=${body?.status}, code=${response.code()})",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                android.util.Log.e("SmartPhonesActivity", "Error fetching subcategories", e)
                Toast.makeText(
                    this@SmartPhonesActivity,
                    "Error: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}