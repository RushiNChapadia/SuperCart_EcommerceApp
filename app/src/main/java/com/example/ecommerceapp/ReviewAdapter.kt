package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.databinding.ItemReviewBinding
import com.example.ecommerceapp.model.Review

class ReviewAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        with(holder.binding) {
            tvUserName.text = review.full_name
            tvReviewTitle.text = review.review_title
            tvReviewBody.text = review.review
            ratingBar.rating = review.rating.toFloat()
        }
    }

    override fun getItemCount(): Int = reviews.size
}