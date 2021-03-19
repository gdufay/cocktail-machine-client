package com.example.cockailmachine.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cockailmachine.R
import com.example.cockailmachine.database.Cocktail

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    var data = listOf<Cocktail>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item)
    }


    override fun getItemCount() = data.size

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cocktailImage: ImageView = itemView.findViewById(R.id.cocktail_img)
        private val cocktailName: TextView = itemView.findViewById(R.id.cocktail_name)

        fun bind(item: Cocktail) {
            cocktailName.text = item.name
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recipe_item_view, parent, false)

                return ViewHolder(view)
            }
        }
    }

}