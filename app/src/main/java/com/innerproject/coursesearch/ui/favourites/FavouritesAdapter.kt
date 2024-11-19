package com.innerproject.coursesearch.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.innerproject.coursesearch.R
import com.innerproject.coursesearch.databinding.CardItemListBinding
import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.ui.home.HomeViewHolder

class FavouritesAdapter(private var data: List<Course>, private val onItemClickListener: (Course) -> Unit) : RecyclerView.Adapter<FavouritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return FavouritesViewHolder(CardItemListBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val course = data[position]
        holder.bind(course)
        val button = holder.itemView.findViewById<ImageButton>(R.id.ib_favourites)
        button.setOnClickListener {
            onItemClickListener.invoke(course)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}