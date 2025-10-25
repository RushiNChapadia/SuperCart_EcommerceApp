package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.databinding.ItemSpecificationBinding
import com.example.ecommerceapp.model.Specification

class SpecificationAdapter(private val specs: List<Specification>) :
    RecyclerView.Adapter<SpecificationAdapter.SpecViewHolder>() {

    inner class SpecViewHolder(val binding: ItemSpecificationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecViewHolder {
        val binding = ItemSpecificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpecViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecViewHolder, position: Int) {
        val spec = specs[position]
        holder.binding.tvSpecTitle.text = spec.title
        holder.binding.tvSpecValue.text = spec.specification
    }

    override fun getItemCount(): Int = specs.size
}