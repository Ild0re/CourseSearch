package com.innerproject.coursesearch.ui.home

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.innerproject.coursesearch.R
import com.innerproject.coursesearch.databinding.CardItemListBinding
import com.innerproject.coursesearch.di.viewModelModule
import com.innerproject.coursesearch.domain.models.Course

class HomeViewHolder(
    private val binding: CardItemListBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Course) {
        binding.tvTitle.text = item.title
        binding.tvDescription.text = item.summary
        binding.tvRating.text = item.rating
        if (item.publishedDate != null) {
            binding.tvDate.text = item.publishedDate
        } else {
            binding.tvDate.visibility = View.GONE
        }
        if (item.price != "-" && item.price != null) {
            binding.tvCost.text = item.price
        } else {
            binding.tvCost.text = itemView.context.getString(R.string.free)
        }
        Glide.with(itemView)
            .load(item.image)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(binding.ivImage)
        itemView.findViewById<com.google.android.material.button.MaterialButton>(R.id.follow)
            .setOnClickListener {
                val bundle = Bundle()
                bundle.putString("course", Gson().toJson(item))
                itemView.findNavController().navigate(R.id.courseFragment, bundle)
            }
        val favourites = itemView.findViewById<ImageButton>(R.id.ib_favourites)
        if (item.inFavourites) {
            favourites.setImageResource(R.drawable.favourites_fill_icon)
        } else {
            favourites.setImageResource(R.drawable.favourites_item)
        }
    }
}