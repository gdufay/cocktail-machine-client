package com.example.cocktailmachine.cocktaillist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailmachine.R
import com.example.cocktailmachine.database.CocktailWithIngredients

class CocktailListAdapter : RecyclerView.Adapter<CocktailListAdapter.ViewHolder>() {
    var cocktails = listOf<CocktailWithIngredients>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cocktails[position]

        holder.bind(item)
    }


    override fun getItemCount() = cocktails.size

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cocktailImage: ImageView = itemView.findViewById(R.id.cocktail_img)
        private val cocktailName: TextView = itemView.findViewById(R.id.cocktail_name)

        fun bind(item: CocktailWithIngredients) {
            cocktailName.text = item.cocktail.cocktailName
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