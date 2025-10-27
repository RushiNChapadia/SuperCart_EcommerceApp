package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.databinding.ItemAddressBinding
import com.example.ecommerceapp.model.AddressModel


class DeliveryAddressAdapter(
    private var addressList: List<AddressModel>,
    private val onSelect: (AddressModel) -> Unit
) : RecyclerView.Adapter<DeliveryAddressAdapter.AddressViewHolder>() {

    private var selectedPosition = -1

    inner class AddressViewHolder(val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addressList[position]
        val b = holder.binding

        b.tvTitle.text = address.title
        b.tvAddress.text = address.address
        b.radioSelect.isChecked = position == selectedPosition

        //  Use holder.adapterPosition instead (universal)
        b.root.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener

            selectedPosition = pos
            onSelect(addressList[pos])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = addressList.size

    fun updateList(newList: List<AddressModel>) {
        addressList = newList
        notifyDataSetChanged()
    }
}