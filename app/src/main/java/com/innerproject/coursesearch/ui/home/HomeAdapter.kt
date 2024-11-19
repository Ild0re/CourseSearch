package com.innerproject.coursesearch.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.innerproject.coursesearch.R
import com.innerproject.coursesearch.databinding.CardItemListBinding
import com.innerproject.coursesearch.domain.models.Course

class HomeAdapter(private var data: List<Course>, private val onItemClickListener: (Course) -> Unit) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return HomeViewHolder(CardItemListBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
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