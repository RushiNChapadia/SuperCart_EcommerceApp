package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.model.ProductModel
import com.example.ecommerceapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import com.example.ecommerceapp.R
import com.example.ecommerceapp.ProductAdapter
import com.example.ecommerceapp.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf<ProductModel>()
    private var subCategoryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subCategoryId = arguments?.getString("sub_category_id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        // Initialize RecyclerView
        binding.recyclerViewAndroid.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductAdapter(productList) { product ->
            //  When user clicks on product item
            val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
            intent.putExtra("product_id", product.product_id)
            startActivity(intent)
        }
        binding.recyclerViewAndroid.adapter = adapter

        fetchProducts()
        return binding.root
    }

    private fun fetchProducts() {
        lifecycleScope.launch {
            try {
                val response =
                    RetrofitClient.instance.getProductsBySubCategory(subCategoryId!!.toInt())

                if (response.isSuccessful && response.body()?.status == 0) {
                    productList.clear()
                    productList.addAll(response.body()!!.products)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "No products found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(subCategoryId: String): ProductFragment {
            val fragment = ProductFragment()
            val bundle = Bundle()
            bundle.putString("sub_category_id", subCategoryId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
